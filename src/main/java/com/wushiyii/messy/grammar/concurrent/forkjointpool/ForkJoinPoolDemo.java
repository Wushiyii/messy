package com.wushiyii.messy.grammar.concurrent.forkjointpool;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author who
 */
public class ForkJoinPoolDemo {

    /**
     * 1.创建方式
     */
    private void buildUpPool(){
        // 1.8创建方法
        ForkJoinPool pool1 = ForkJoinPool.commonPool();
        // 在1.7也可以使用如下方法创建
        ForkJoinPool pool2 = new ForkJoinPool(5);
    }

    @Slf4j
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CustomRecursiveAction extends RecursiveAction {

        private String workload;
        private static final int THRESHOLD = 4;

        CustomRecursiveAction(String workload) {
            this.workload = workload;
        }

        @Override
        protected void compute() {
            if (workload.length() > THRESHOLD) {
                ForkJoinTask.invokeAll(createSubTasks());
            } else {
                process(workload);
            }
        }

        private List<CustomRecursiveAction> createSubTasks() {
            List<CustomRecursiveAction> subTasks = new ArrayList<>();

            String partOne = workload.substring(0, workload.length() / 2);
            String partTwo = workload.substring(workload.length() / 2);

            subTasks.add(new CustomRecursiveAction(partOne));
            subTasks.add(new CustomRecursiveAction(partTwo));

            return subTasks;
        }

        private void process(String work) {
            String result = work.toUpperCase();
            log.info("This result - (" + result + ") - was processed by "
                    + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {

        // 1.8创建方法
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        // 在1.7也可以使用如下方法创建
//        ForkJoinPool forkJoinPool2 = new ForkJoinPool(5);

        CustomRecursiveAction customRecursiveTask = new CustomRecursiveAction("whatisyourname");
        forkJoinPool.execute(customRecursiveTask);

    }
}
