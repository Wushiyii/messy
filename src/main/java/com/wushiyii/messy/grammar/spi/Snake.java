package com.wushiyii.messy.grammar.spi;

public class Snake implements Animal {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Snake.");
    }
}