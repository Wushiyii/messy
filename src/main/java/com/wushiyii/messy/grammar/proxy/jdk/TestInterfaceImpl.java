package com.wushiyii.messy.grammar.proxy.jdk;

/**
 * @author wgq
 * @date 2020/3/9 7:53 下午
 */
public class TestInterfaceImpl implements TestInterface {
    @Override
    public void fun(String funnyStr) {
        System.out.println("TestInterfaceImpl : " + funnyStr);
    }
}
