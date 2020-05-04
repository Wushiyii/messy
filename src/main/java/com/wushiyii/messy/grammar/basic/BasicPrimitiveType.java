package com.wushiyii.messy.grammar.basic;

/**
 * @author wgq
 * @date 2020/5/3 10:25 下午
 */
public class BasicPrimitiveType {

    public static void shortCompare() {

        //可正常编译，因为 += 会隐性使用强转(short)
        short s1 =  1;
        s1 += 1;

        //不可正常编译
//        s1 = s1 + 1;
        System.out.println(s1);
    }

    public static void intCompare() {
        Integer i1 = 100, i2 = 100, i3 = 130, i4 = 130;

        //Integer构造的时候用了缓存,默认-128 ～ + 128
        System.out.println(i1 == i2);// true
        System.out.println(i3 == i4);// false
    }

    public static void stringCompare() {
        String s1 = new StringBuilder("go").append("od").toString();
        // System.initializeSystemClass会触发一部分字符串的初始化，导致intern返回的并不等于新建的
        String s2 = new StringBuilder("1.8.0_").append("201").toString();
        System.out.println(s1.intern() == s1);
        System.out.println(s2.intern() == s2);
    }

    public static void stringCompare2() {
        String s1 = "helloWorld";
        String s2 = new String("helloWorld");
        String s3 = "hello";
        String s4 = "World";
        String s5 = "hello" + "World";
        String s6 = s3 + s4;

        System.out.println(s1 == s2);
        System.out.println(s1 == s2.intern());



        System.out.println("s1:" + System.identityHashCode(s1));
        System.out.println("s2:" + System.identityHashCode(s2));

        System.out.println("s3:" + System.identityHashCode(s3));
        System.out.println("s4:" + System.identityHashCode(s4));

        System.out.println("s5:" + System.identityHashCode(s5));
        System.out.println("s6:" + System.identityHashCode(s6));

    }



    public static void main(String[] args) {
        stringCompare2();
    }
}
