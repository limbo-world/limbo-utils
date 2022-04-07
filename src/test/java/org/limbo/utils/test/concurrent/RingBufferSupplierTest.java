package org.limbo.utils.test.concurrent;

import org.junit.Before;
import org.junit.Test;
import org.limbo.utils.concurrent.buffer.DefaultRingBufferSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Brozen
 * @since 2022-04-06
 */
public class RingBufferSupplierTest {


    private IdRepository idRepository;

    @Before
    public void initIdRepository() {
        this.idRepository = new IdRepository();
    }


    @Test
    public void testRingBufferSupplier() throws InterruptedException {

        // 初始化生产者，缓冲区大小16，每次批量加载9条数据
        int bufferSize = 16;
        int loadSize = (bufferSize >>> 1) + 1;
        DefaultRingBufferSupplier<List<Long>, Long> idSupplier = new DefaultRingBufferSupplier<>(
                bufferSize, false, () -> idRepository.batchGetIds(loadSize));
        idSupplier.start();

        // 启动10个线程，每个线程获取100次
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println("生成ID: " + idSupplier.get());
                }
            });
            threads.add(thread);
            thread.start();
        }

        // 等待所有线程执行完成
        for (Thread thread : threads) {
            thread.join();
        }

        idSupplier.shutdown();
    }



    static class IdRepository {

        private final AtomicLong table = new AtomicLong(0L);

        /**
         * 模拟批量从DB读取id
         * @param size 读取的id数量
         * @return 取值
         */
        public List<Long> batchGetIds(int size) {
            System.out.println("触发模拟DB读取");

            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ids.add(table.incrementAndGet());
            }

            return ids;
        }


    }

}
