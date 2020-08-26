package com.wushiyii.messy;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wgq
 * @date 2020/8/20 2:54 下午
 */
public class GeneralTest {

    private volatile AtomicLong counter = new AtomicLong(1);
    private volatile AtomicLong result = new AtomicLong(0);

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int times = 0;
                while (times < 2000) {
                    result.addAndGet(counter.getAndIncrement());
                    times++;
                }
            }).start();
        }

        Thread.sleep(2000);

        System.out.println(counter);
        System.out.println(result);

    }

}
