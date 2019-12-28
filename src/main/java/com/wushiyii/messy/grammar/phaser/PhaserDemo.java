package com.wushiyii.messy.grammar.phaser;


import lombok.Data;

import java.util.concurrent.Phaser;

/**
 * <b>Phaser</b>: 由java7中推出，是Java SE 7中新增的一个使用同步工具，在功能上面它与CyclicBarrier、CountDownLatch有些重叠，但是它提供了更加灵活、强大的用法。<br>
 *
 * <b>CyclicBarrier</b>:允许一组线程互相等待，直到到达某个公共屏障点。它提供的await()可以实现让所有参与者在临界点到来之前一直处于等待状态。<br>
 *
 * <b>CountDownLatch</b>:在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。它提供了await()、countDown()两个方法来进行操作。<br>
 *
 * 在Phaser中，它把多个线程协作执行的任务划分为多个阶段，编程时需要明确各个阶段的任务，每个阶段都可以有任意个参与者，线程都可以随时注册并参与到某个阶段 <br>
 * @author who
 */
public class PhaserDemo {

    @Data
    public static class RunnableDemo implements Runnable {

        private Phaser phaser;

        public RunnableDemo(Phaser phaser) {
            this.phaser = phaser;
        }


        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "继续执行任务...");
        }
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new RunnableDemo(phaser)).start();
        }
    }
}
