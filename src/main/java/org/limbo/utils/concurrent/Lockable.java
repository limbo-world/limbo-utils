/*
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.limbo.utils.concurrent;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Brozen
 * @date 2020/7/3 4:14 PM
 * @email brozen@qq.com
 */
public class Lockable<T> {

    private final ReentrantReadWriteLock lock;

    private T object;

    public Lockable() {
        this(null);
    }

    public Lockable(T object) {
        this.lock = new ReentrantReadWriteLock();
        this.object = object;
    }

    /**
     * 设置可锁变量的值，申请写锁
     */
    public void set(T object) {
        ReentrantReadWriteLock.WriteLock writeLock = writeLock();
        writeLock.lock();
        try {
            this.object = object;
        } finally {
            writeLock.unlock();
        }
    }
    /**
     * 读取可锁变量的值，申请读锁
     */
    public T get() {
        ReentrantReadWriteLock.ReadLock readLock = readLock();
        readLock.lock();
        try {
            return object;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 返回写锁
     */
    protected ReentrantReadWriteLock.WriteLock writeLock() {
        return lock.writeLock();
    }

    /**
     * 返回读锁
     */
    protected ReentrantReadWriteLock.ReadLock readLock() {
        return lock.readLock();
    }

    /**
     * 申请锁后执行操作
     * @param operation 操作
     * @param mode 加锁模式
     * @see Mode
     */
    public void run(Consumer<T> operation, Mode mode) {
        switch (mode) {
            case READ:
                runInReadLock(operation);
                break;

            case WRITE:
                runInWriteLock(operation);

            default:
                throw new IllegalArgumentException("未知的加锁模式：" + mode);
        }
    }

    /**
     * 在申请写锁后执行操作
     */
    private void runInWriteLock(Consumer<T> operation) {
        Objects.requireNonNull(operation, "operation");
        ReentrantReadWriteLock.WriteLock writeLock = writeLock();
        writeLock.lock();
        try {
            operation.accept(object);
        } finally {
            writeLock.unlock();
        }
    }
    /**
     * 在申请读锁后执行操作
     */
    private void runInReadLock(Consumer<T> operation) {
        Objects.requireNonNull(operation, "operation");
        ReentrantReadWriteLock.ReadLock readLock = readLock();
        readLock.lock();
        try {
            operation.accept(object);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 在申请锁后执行操作，并返回操作的结果
     * @param operation 操作
     * @param mode 加锁模式
     * @param <R> 操作返回结果类型
     * @return 操作返回结果
     */
    public <R> R invoke(Function<T, R> operation, Mode mode) {
        switch (mode) {
            case READ:
                return invokeInReadLock(operation);

            case WRITE:
                return invokeInWriteLock(operation);

            default:
                throw new IllegalArgumentException("未知的加锁模式：" + mode);
        }
    }

    /**
     * 在申请写锁后执行操作，并返回
     */
    private <R> R invokeInWriteLock(Function<T, R> operation) {
        Objects.requireNonNull(operation, "operation");
        ReentrantReadWriteLock.WriteLock writeLock = writeLock();
        writeLock.lock();
        try {
            return operation.apply(object);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 在申请读锁后执行操作，并返回
     */
    private <R> R invokeInReadLock(Function<T, R> operation) {
        Objects.requireNonNull(operation, "operation");
        ReentrantReadWriteLock.ReadLock readLock = readLock();
        readLock.lock();
        try {
            return operation.apply(object);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 加锁模式
     */
    enum Mode {
        /**
         * 读锁
         */
        READ,

        /**
         * 写锁
         */
        WRITE
    }

}
