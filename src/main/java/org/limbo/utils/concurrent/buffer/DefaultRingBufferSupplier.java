package org.limbo.utils.concurrent.buffer;

import com.lmax.disruptor.RingBuffer;
import org.limbo.utils.concurrent.Ref;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Brozen
 * @since 2022-04-06
 */
public class DefaultRingBufferSupplier<C extends Collection<T>, T> extends RingBufferSupplier<T> {

    /**
     * 真正负责数据加载的生产者
     */
    private final Supplier<C> collectionSupplier;

    /**
     * @param bufferSize 缓冲区大小，为保证性能，建议是2的n次幂
     * @param eagerLoad 是否在启动生产者后立即进行数据加载。如传入false，则在第一次调用{@link #get()}方法时才会触发数据加载。
     * @param supplier 真正进行数据生产的生产者，将批量生产数据，并以集合形式返回。
     */
    public DefaultRingBufferSupplier(int bufferSize, boolean eagerLoad, Supplier<C> supplier) {
        super(bufferSize, eagerLoad);
        this.collectionSupplier = Objects.requireNonNull(supplier);
    }

    /**
     * @param buffer 生产者使用的缓冲区
     * @param eagerLoad 是否在启动生产者后立即进行数据加载。如传入false，则在第一次调用{@link #get()}方法时才会触发数据加载。
     * @param supplier 真正进行数据生产的生产者，将批量生产数据，并以集合形式返回。
     */
    public DefaultRingBufferSupplier(RingBuffer<Ref<T>> buffer, boolean eagerLoad, Supplier<C> supplier) {
        super(buffer, eagerLoad);
        this.collectionSupplier = Objects.requireNonNull(supplier);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadData() {
        publishData(this.collectionSupplier.get());
    }

}
