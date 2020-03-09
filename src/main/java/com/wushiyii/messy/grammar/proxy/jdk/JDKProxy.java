package com.wushiyii.messy.grammar.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author wgq
 * @date 2020/3/9 7:55 下午
 */
@Slf4j
public class JDKProxy implements InvocationHandler {

    private Object targetObj;

    public Object newProxy(Object targetObj) {
        this.targetObj = targetObj;
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), targetObj.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("before invoke, proxy={}, method={}, args={}", proxy, method, args);
        Object invoke = method.invoke(targetObj, args);
        log.info("after invoke, proxy={}, method={}, args={}", proxy, method, args);
        return invoke;
    }


    public static void main(String[] args) {
        JDKProxy proxy = new JDKProxy();
        TestInterface testInterface = (TestInterface) proxy.newProxy(new TestInterfaceImpl());
        testInterface.fun("test test");
    }
}
