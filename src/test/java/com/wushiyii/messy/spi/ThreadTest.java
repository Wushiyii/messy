package com.wushiyii.messy.spi;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.function.Function;

/**
 * @author wgq
 * @date 2020/8/11 6:03 下午
 */
public class ThreadTest {

    @Test
    public void printTheadInfo() {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }

    public static Function<String , Integer> demoFunction = key -> {
        System.out.println(key);
        return Integer.parseInt(key);
    };

    @Test
    public void functionTest() {
        final Integer res = demoFunction.apply("123");
        System.out.println(res);
    }

    @Test
    public void deadLock() {

        String str1 = "hello";
        String str2 = "world";

        new Thread(() -> {
            synchronized(str1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(str1);

                synchronized (str2) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(str2);
                }
            }
        }).start();

        new Thread(() -> {
            synchronized(str2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(str2);

                synchronized (str1) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(str1);
                }
            }
        }).start();


    }
}
