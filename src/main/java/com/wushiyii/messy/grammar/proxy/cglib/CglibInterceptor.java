package com.wushiyii.messy.grammar.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wgq
 * @date 2020/3/19 3:48 下午
 */
public class CglibInterceptor implements MethodInterceptor {

    /**
     *
     * @param object 需要代理到对象
     * @param method 需要代理的方法
     * @param objects 入参（需要为包装类）
     * @param methodProxy 代理方法
     * @return result
     * @throws Throwable throwable
     */
    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        /**
         * 注意这里是调用 invokeSuper 而不是 invoke，否则死循环;
         * methodProxy.invokeSuper执行的是原始类的方法;
         * method.invoke执行的是子类的方法
         */
        Object res = methodProxy.invokeSuper(object, objects);
        after();
        return res;
    }

    private void before() {
        System.out.println("proxy-before");
    }

    private void after() {
        System.out.println("proxy-after");
    }
}
