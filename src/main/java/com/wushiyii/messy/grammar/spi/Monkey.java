package com.wushiyii.messy.grammar.spi;


public class Monkey implements Animal {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Monkey.");
    }
}