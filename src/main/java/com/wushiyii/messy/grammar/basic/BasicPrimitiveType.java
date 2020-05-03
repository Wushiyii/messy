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



    public static void main(String[] args) {
        intCompare();
    }
}
