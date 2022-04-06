package org.limbo.utils.concurrent.buffer;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 异步加载数据的带缓存生产者。
 *
 * @author Brozen
 * @since 2022-04-06
 */
@Slf4j
public abstract class AsyncLoadBufferedSupplier<T> extends BufferedSupplier<T> {

    /**
     * 加载数据时用到的线程池
     */
    private final Executor loadExecutor;

    /**
     * 异步加载任务
     */
    private AsyncLoadTask loadTask;


    public AsyncLoadBufferedSupplier(int bufferSize, boolean eagerLoad) {
        this(bufferSize, eagerLoad, Executors.newSingleThreadExecutor());
    }


    public AsyncLoadBufferedSupplier(int bufferSize, boolean eagerLoad, Executor loadExecutor) {
        super(bufferSize, eagerLoad);
        this.loadExecutor = loadExecutor;
    }


    /**
     * 启动异步加载任务
     */
    @Override
    protected void onStart() {
        this.loadTask = new AsyncLoadTask();
        this.loadExecutor.execute(this.loadTask);
    }


    /**
     * 关闭生产者时再触发一次加载，防止关闭生产者时加载任务正在等待，从而永远无法被唤醒。
     */
    @Override
    protected void onShutdown() {
        if (this.loadTask != null) {
            this.loadTask.permitLoadData();
            this.loadTask = null;
        }
    }


    /**
     * 在线程池中提交数据加载任务，并触发一次加载
     */
    @Override
    protected void loadData() {
        Objects.requireNonNull(this.loadTask).permitLoadData();
    }


    /**
     * 执行数据加载
     */
    protected abstract void doLoadData();


    /**
     * 当加载数据发生异常时的回调。
     */
    protected void onLoadError(Throwable throwable) {
        // ignore
    }


    /**
     * 异步数据加载任务
     */
    class AsyncLoadTask implements Runnable {

        private final long PARK_DURATION = Duration.ofMillis(100).toNanos();

        private final AtomicBoolean permitted = new AtomicBoolean(false);

        @Override
        public void run() {
            while (isRunning()) {
                try {
                    // 检测是否被许可加载数据
                    if (!permitted.compareAndSet(true, false)) {
                        LockSupport.parkNanos(PARK_DURATION);
                        continue;
                    }

                    // 再次检测
                    if (!isRunning()) {
                        break;
                    }

                    doLoadData();
                } catch (Exception e) {
                    log.error("数据加载任务异常", e);
                    onLoadError(e);
                }
            }
        }


        /**
         * 发布一个加载数据的许可
         */
        public void permitLoadData() {
            // 使用原子boolean变量CAS更新许可，可以防止重复下发许可，保证多次调用许可方法时，也只执行一次数据加载动作
            // 其他的类似Semaphore、CyclicBarrier也行，但还是有锁的，不如原子操作方便快捷
            this.permitted.compareAndSet(false, true);
        }

    }


}
