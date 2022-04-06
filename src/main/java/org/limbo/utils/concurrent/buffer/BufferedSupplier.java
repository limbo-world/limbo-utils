package org.limbo.utils.concurrent.buffer;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 带缓存的数据生产者。
 *
 * @author Brozen
 * @since 2022-04-06
 */
public abstract class BufferedSupplier<T> implements Supplier<T> {

    /**
     * 生产者名称，用于日志跟踪
     */
    @Setter
    @Getter
    private String name;

    /**
     * 缓冲区大小
     */
    @Getter
    private final long bufferSize;

    /**
     * 是否在初始化supplier之后，立即初始化缓存队列。
     */
    @Getter
    private final boolean eagerLoad;

    /**
     * 缓冲数据加载因子，当缓存中剩余的数据量低于缓存容量的0.75时，会触发数据加载
     */
    @Setter
    private float loadFactor;

    /**
     * 此生产者是否在运行中
     */
    private volatile boolean running;


    public BufferedSupplier(long bufferSize) {
        this(bufferSize, false);
    }


    public BufferedSupplier(long bufferSize, boolean eagerLoad) {
        this(bufferSize, eagerLoad, 0.75f);
    }


    public BufferedSupplier(long bufferSize, boolean eagerLoad, float loadFactor) {
        this.bufferSize = bufferSize;
        this.eagerLoad = eagerLoad;
        this.loadFactor = loadFactor;

        this.running = false;
    }


    /**
     * 获取缓存中剩余可用的元素数量
     */
    protected abstract long getRemainingSize();


    /**
     * 启动此生产者
     */
    public final void start() {
        // 设置状态
        this.running = true;

        // 调用钩子方法
        this.onStart();

        // 加载数据
        if (this.eagerLoad) {
            loadData();
        }
    }


    /**
     * 生产者启动后的钩子
     */
    protected abstract void onStart();


    /**
     * 此生产者是否在运行中。
     */
    public boolean isRunning() {
        return this.running;
    }


    /**
     * 关闭此生产者。
     */
    public final void shutdown() {
        this.running = false;
    }


    /**
     * 生产者关闭后钩子
     */
    protected abstract void onShutdown();


    /**
     * 检测缓冲区剩余数据量，当数据不足时需要加载数据
     */
    protected void checkAndLoadData() {
        if (needLoadData()) {
            loadData();
        }
    }


    /**
     * 检测是否需要加载数据
     */
    protected boolean needLoadData() {
        long loadThreshold = (long) (this.bufferSize * this.loadFactor);
        return getRemainingSize() < loadThreshold;
    }


    /**
     * 加载数据到缓冲区
     */
    protected abstract void loadData();


    /**
     * 发布数据到缓存
     * @param data 待发布的数据
     */
    protected abstract void publishData(Collection<T> data);


    /**
     * 读操作发生异常时，调用此方法进行处理。
     * 如果返回了非null值，则使用此方法返回的值作为读操作的结果，并认为异常被处理。
     * 如果返回了null值，则读操作返回null，并认为异常被处理。
     * 如果重新抛出了异常，则认为异常未被处理，并且抛出的异常将取代原异常向上抛出。
     *
     * @param throwable 导致读操作失败的异常
     * @return 异常处理结果
     */
    protected T onGetError(Throwable throwable) {
        throw new IllegalStateException("读取数据异常", throwable);
    }

}
