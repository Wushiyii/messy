package com.wushiyii.messy.grammar.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author wgq
 * @date 2020/3/19 3:46 下午
 */
public class UserServiceImpl {

    public void doSomething(String str) {
        System.out.println("doing something about : " + str);
    }


    public static void main(String[] args) {
        // CGLIB
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        //也可以通过匿名实现类实现
        enhancer.setCallback(new CglibInterceptor());
        UserServiceImpl userService = (UserServiceImpl) enhancer.create();
        userService.doSomething("eat");
    }
}
