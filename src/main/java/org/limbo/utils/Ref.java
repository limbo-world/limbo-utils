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

package org.limbo.utils;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * 引用，可以在lambda或匿名类中用来修改变量
 * 线程安全
 *
 * @author Brozen
 * @date 2019/12/4
 */
@Slf4j
@ToString
public class Ref<T> {

    private static final Unsafe UNSAFE;
    private static final long VALUE;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
            VALUE = UNSAFE.objectFieldOffset(Ref.class.getDeclaredField("value"));
        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    private final T value;

    public Ref() {
        this(null);
    }

    public Ref(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        T copy;
        do {
            copy = this.value;
        } while (!UNSAFE.compareAndSwapObject(this, VALUE, copy, value));
    }

    public void setIfAbsent(T value) {
        UNSAFE.compareAndSwapObject(this, VALUE, null, value);
    }

    public boolean compareAndSet(T oldOne, T newOne) {
        return UNSAFE.compareAndSwapObject(this, VALUE, oldOne, newOne);
    }

    public Optional<T> optional() {
        return Optional.ofNullable(this.value);
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
