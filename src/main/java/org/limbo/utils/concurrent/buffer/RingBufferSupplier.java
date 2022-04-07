package org.limbo.utils.concurrent.buffer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.limbo.utils.concurrent.Ref;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Disruptor 的 RingBuffer 实现的带缓存生产者。
 *
 * @author Brozen
 * @since 2022-04-06
 */
@Slf4j
public abstract class RingBufferSupplier<T> extends AsyncLoadBufferedSupplier<T> {

    /**
     * 读操作控制信号量，并发读时，需要留一个槽位给生产者写入数据，因此信号量数量是 <code>bufferSize - 1</code>
     */
    private final Semaphore accessorController;

    /**
     * 缓存队列
     */
    private final RingBuffer<Ref<T>> buffer;

    /**
     * 读索引
     */
    private final Sequence sequence;

    /**
     * 读操作阻塞器
     */
    private final SequenceBarrier readBarrier;


    public RingBufferSupplier(int bufferSize, boolean eagerLoad) {
        this(RingBuffer.createSingleProducer(Ref::new, bufferSize), eagerLoad);
    }


    public RingBufferSupplier(RingBuffer<Ref<T>> buffer, boolean eagerLoad) {
        super(buffer.getBufferSize(), eagerLoad);
        this.accessorController = new Semaphore(buffer.getBufferSize() - 1);
        this.buffer = buffer;
        this.sequence = new Sequence();
        this.buffer.addGatingSequences(sequence);
        this.readBarrier = buffer.newBarrier(sequence);
    }


    /**
     * 从缓存的值中取出一个。
     */
    @Override
    public T get() {
        // 检测是否还在运行
        if (!isRunning()) {
            return onGetError(new IllegalStateException("此生产者已经停止：" + getName()));
        }

        T value = null;
        boolean accessAllowed = false;
        try {
            // 申请读取，5秒超时
            accessAllowed = this.accessorController.tryAcquire(5, TimeUnit.SECONDS);
            if (accessAllowed) {

                // 检测是否需要加载数据
                checkAndLoadData();
                // 读取数据
                return doGet();
            }

        } catch (InterruptedException e) {
            log.error("请求读取异常", e);
            value = onGetError(e);
        } finally {
            if (accessAllowed) {
                this.accessorController.release();
            }
        }

        return value;
    }


    /**
     * 执行数据获取操作
     */
    private T doGet() {
        T value;
        try {
            long nextSequence = this.sequence.get();
            long availableSequence = Long.MIN_VALUE;
            boolean nextSequenceGot = false;

            while (true) {
                // 如果没有获取过下一个读索引，需要计算出下一个读索引的位置；读取后，CAS更新全局读索引，
                // 保证并发读情况下，一个读索引只会被一个线程（当前线程）拿到
                if (!nextSequenceGot) {
                    do {
                        nextSequence = this.sequence.get() + 1L;
                    } while (!this.sequence.compareAndSet(nextSequence - 1L, nextSequence));
                    nextSequenceGot = true;
                }

                // 如果有数据可读，则读取；否则重新获取可读索引；
                if (nextSequence <= availableSequence) {
                    Ref<T> ref = buffer.get(nextSequence);
                    // 读数据，并清空value，因为Disruptor不会自动清理，需要手动处理
                    value = ref.get();
                    ref.set(null);
                    return value;
                } else {
                    // 走到这里，说明第一次进入循环；或多线程并发读时，缓存中数据不足，不够读取；
                    // 此时需要获取当前buffer的可读索引，然后重新判断是否有数据可读（可读索引 >= 下一个读索引）：
                    //     1. 可读则读取数据并返回（即上面的if块）；
                    //     2. 否则重新获取可读索引（此处的else块），并重新判断（再次进入while循环）
                    availableSequence = readBarrier.waitFor(nextSequence);
                }
            }

        } catch (Exception e) {
            log.error("读取buffer发生异常", e);
            value = onGetError(e);
        }

        return value;
    }


    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected long getRemainingSize() {
        long remainingCapacity = this.buffer.remainingCapacity();
        int bufferSize = this.buffer.getBufferSize();
        long remainingSize = bufferSize - remainingCapacity;
        return remainingSize < 0 ? 0 : remainingSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onShutdown() {
        super.onShutdown();
        this.readBarrier.alert();
    }


    /**
     * {@inheritDoc}
     * @param data 待发布的数据
     */
    @Override
    protected void publishData(Collection<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        for (T datum : data) {
            long sequence = this.buffer.next();
            try {
                Ref<T> cacheable = this.buffer.get(sequence);
                cacheable.set(datum);
            } finally {
                this.buffer.publish(sequence);
            }
        }
    }

}
