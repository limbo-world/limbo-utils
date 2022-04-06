/*
 *
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.limbo.utils.concurrent;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 引用，可以在lambda或匿名类中用来修改变量
 * 线程安全
 *
 * @author Brozen
 * @since 2019/12/4
 */
@Slf4j
@ToString
public class Ref<T> {

    /**
     * 原子更新引用的对象
     */
    private static final AtomicReferenceFieldUpdater<Ref, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(Ref.class, Object.class, "value");

    /**
     * 引用的对象
     */
    private volatile T value;

    /**
     * 构造一个引用值为空的 Ref
     */
    public Ref() {
        this(null);
    }

    /**
     * 构造一个引用指定值的 Ref
     * @param value 引用的对象
     */
    public Ref(T value) {
        this.value = value;
    }

    /**
     * 获取 Ref 当前引用的对象
     * @return Ref 当前引用的对象
     */
    public T get() {
        return this.value;
    }

    /**
     * 设置 Ref 引用的对象，使用 CAS 方式更新，当多个线程并发调用时，多个值会依次被设置给引用。
     * @param value 引用的对象
     */
    public void set(T value) {
        T copy;
        do {
            copy = (T) this.value;
        } while (!UPDATER.compareAndSet(this, copy, value));
    }

    /**
     * 如果 Ref 引用对象为 null，则设置为指定的对象。使用 CAS 方式更新。
     * @param value 引用的对象
     */
    public void setIfAbsent(T value) {
        UPDATER.compareAndSet(this, null, value);
    }

    /**
     * CAS 更新引用的对象。
     * @param oldOne 此 Ref 当前引用的对象
     * @param newOne 更新后 Ref 引用的对象
     * @return CAS 更新是否成功。如更新时 Ref 当前引用的对象与入参 oldOne 不匹配，则返回 false。
     */
    public boolean compareAndSet(T oldOne, T newOne) {
        return UPDATER.compareAndSet(this, oldOne, newOne);
    }

    /**
     * 将 Ref 当前引用的对象转为 Optional。
     * 返回的 Optional 中引用的 value 是调用此方法时 value 的瞬时值，因此后续对 Ref 的修改无法体现在返回的 Optional 中。
     * @return 持有调用方法时刻 value 的Optional
     */
    public Optional<T> optional() {
        return Optional.ofNullable(((T) this.value));
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Ref) {
            obj = ((Ref<?>) obj).get();
        }

        return Objects.equals(value, obj);
    }

}
