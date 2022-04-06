package org.limbo.utils.concurrent.buffer;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Brozen
 * @since 2022-04-06
 */
public class Cacheable<T> {

    /**
     * 被缓存的值
     */
    private T cached;

    public Cacheable() {
        this(null);
    }


    public Cacheable(T cached) {
        this.cached = cached;
    }


    /**
     * 获取被缓存的值
     */
    @Nullable
    public T value() {
        return this.cached;
    }


    /**
     * 设置被缓存的值
     */
    public void value(@Nullable T t) {
        this.cached = t;
    }


    /**
     * 转换为{@link Optional}
     */
    public Optional<T> toOptional() {
        return Optional.ofNullable(this.cached);
    }

}
