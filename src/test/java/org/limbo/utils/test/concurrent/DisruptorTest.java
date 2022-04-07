package org.limbo.utils.test.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;
import org.limbo.utils.concurrent.Ref;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Brozen
 * @since 2022-04-07
 */
public class DisruptorTest {



    /**
     * 单生产者、单消费者测试
     */
    @Test
    public void oneToOne() throws InterruptedException {
        // 初始化 Disruptor
        Disruptor<Ref<String>> disruptor = new Disruptor<>(
                Ref::new, 16,
                new ThreadFactoryBuilder().setNameFormat("Disruptor-Pool-%d").build(),
                ProducerType.SINGLE, new BlockingWaitStrategy()
        );


        // 生产者
        AtomicLong counter = new AtomicLong(0L);
        Thread producerThread = new Thread(() -> {

            while (true) {

                // 发布一个消息
                String msg = "" + counter.getAndIncrement();
                System.out.println(currentThread() + "生产消息: " + msg);
                disruptor.publishEvent((ref, sequence) -> ref.set(msg));

                // 线程暂停100ms，大约1秒钟10条消息
                LockSupport.parkNanos(Duration.ofMillis(100).toNanos());
            }
        });


        // 注册消费者，消费50条消息后停止
        CountDownLatch latch = new CountDownLatch(50);
        disruptor.handleEventsWith((ref, sequence, endOfBatch) -> {
            if (latch.getCount() <= 0) {
                return;
            }

            System.out.println(currentThread() + "消费消息:" + ref.get());
            ref.set(null);
            latch.countDown();

            // 测试消费速度低于生产速度
            // LockSupport.parkNanos(Duration.ofMillis(1000).toNanos());
        });

        // 启动Disruptor、启动生产者线程
        disruptor.start();
        producerThread.start();

        // 等待50个消费完成
        latch.await();
        disruptor.shutdown();
    }



    /**
     * 多生产者、多消费者测试（广播消费）
     */
    @Test
    public void manyToManyBroadcast() throws InterruptedException {
        // 初始化 Disruptor
        Disruptor<Ref<String>> disruptor = new Disruptor<>(
                Ref::new, 16,
                new ThreadFactoryBuilder().setNameFormat("Disruptor-Pool-%d").build(),
                ProducerType.MULTI, new BlockingWaitStrategy()
        );

        // 10个生产者同时生产数据
        AtomicLong counter = new AtomicLong(0L);
        List<Thread> producerThreads = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(() -> {
                    while (true) {
                        // 发布一个消息
                        String msg = "" + counter.getAndIncrement();
                        System.out.println(currentThread() + "生产消息: " + msg);
                        disruptor.publishEvent((ref, sequence) -> ref.set(msg));

                        // 线程暂停1000ms，1秒钟1条消息
                        LockSupport.parkNanos(Duration.ofMillis(100).toNanos());
                    }
                }))
                .collect(Collectors.toList());

        // 注册2个消费者，消费者A消费25条消息后停止，消费者B消费32条数据后停止
        LimitedConsumer consumerA = new LimitedConsumer("消费者A", 25);
        disruptor.handleEventsWith(consumerA);
        LimitedConsumer consumerB = new LimitedConsumer("消费者B", 32);
        disruptor.handleEventsWith(consumerB);

        // 启动Disruptor、启动生产者线程
        disruptor.start();
        producerThreads.forEach(Thread::start);

        // 等待消费者A、B消费完成
        consumerA.await();
        consumerB.await();
        disruptor.shutdown();
    }


    /**
     * 多生产者、多消费者测试（广播消费）
     */
    @Test
    public void manyToManyGroup() throws InterruptedException {
        // 初始化 Disruptor
        Disruptor<Ref<String>> disruptor = new Disruptor<>(
                Ref::new, 16,
                new ThreadFactoryBuilder().setNameFormat("Disruptor-Pool-%d").build(),
                ProducerType.MULTI, new BlockingWaitStrategy()
        );

        // 10个生产者同时生产数据
        AtomicLong counter = new AtomicLong(0L);
        List<Thread> producerThreads = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(() -> {
                    while (true) {
                        // 发布一个消息
                        String msg = "" + counter.getAndIncrement();
                        System.out.println(currentThread() + "生产消息: " + msg);
                        disruptor.publishEvent((ref, sequence) -> ref.set(msg));

                        // 线程暂停1000ms，1秒钟1条消息
                        LockSupport.parkNanos(Duration.ofMillis(100).toNanos());
                    }
                }))
                .collect(Collectors.toList());

        // 注册2个消费者到同一个消费者组，改组消费者消费总共50条消息后停止消费
        CountDownLatch latch = new CountDownLatch(50);
        GroupedLimitedConsumer consumerA = new GroupedLimitedConsumer("消费者A", latch);
        GroupedLimitedConsumer consumerB = new GroupedLimitedConsumer("消费者B", latch);
        disruptor.handleEventsWithWorkerPool(consumerA, consumerB);

        // 启动Disruptor、启动生产者线程
        disruptor.start();
        producerThreads.forEach(Thread::start);

        // 等待消费者组消费完成
        latch.await();
        disruptor.shutdown();
    }




    static String currentThread() {
        return Thread.currentThread().getName();
    }



    static class LimitedConsumer implements EventHandler<Ref<String>> {

        private final String consumerName;

        private final CountDownLatch latch;

        public LimitedConsumer(String consumerName, int limit) {
            this.consumerName = consumerName;
            this.latch = new CountDownLatch(limit);
        }

        @Override
        public void onEvent(Ref<String> event, long sequence, boolean endOfBatch) throws Exception {
            if (latch.getCount() <= 0) {
                return;
            }

            System.out.println(currentThread() + consumerName + "消费消息:" + event.get());
            latch.countDown();
        }

        /**
         * 等待消费者处理了指定条数的数据
         */
        public void await() throws InterruptedException {
            latch.await();
        }
    }

    static class GroupedLimitedConsumer implements WorkHandler<Ref<String>> {

        private final String consumerName;

        private final CountDownLatch latch;

        public GroupedLimitedConsumer(String consumerName, CountDownLatch latch) {
            this.consumerName = consumerName;
            this.latch = latch;
        }

        @Override
        public void onEvent(Ref<String> event) throws Exception {
            if (latch.getCount() <= 0) {
                return;
            }

            System.out.println(currentThread() + consumerName + "消费消息:" + event.get());
            latch.countDown();
        }
    }

}
