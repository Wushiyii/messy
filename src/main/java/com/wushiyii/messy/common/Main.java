package com.wushiyii.messy.common;

/**
 * @author wgq
 * @date 2020/3/5 9:53 下午
 */
public class Main {

    Runnable r1 = () -> System.out.println(this);
    Runnable r2 = () -> System.out.println(toString());

    public static void main(String[] args) {
        new Main().r1.run();
        new Main().r2.run();
    }

    @Override
    public String toString() {
        return "Main.toString";
    }
}
