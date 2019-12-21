package com.wushiyii.messy.grammar.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author who
 */
public class StreamDemo {

    /**
     * 创建普通流
     */
    private void buildStream(){
        // 1.单变量创建流
        Stream stream = Stream.of("a", "b", "c", "d", "e");
        // 2.根据数组创建
        String[] arr = {"a", "b", "c", "d", "e"};
        stream = Stream.of(arr);
        // 3.根据集合创建
        stream = Stream.of(Arrays.asList("a", "b", "c", "d", "e"));
    }

    /**
     * 创建数值流
     */
    private void buildDigitalStream() {
        // 1.单变量创建
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        // 2.前开后闭区间创建
        intStream = IntStream.range(0, 5);
        // 3.前开后开区间创建
        intStream = IntStream.rangeClosed(0, 5);
    }

    /**
     * 转换流到其他数据结构
     */
    private void transferStream() {
        // 1.转换为数组
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Integer[] arr = stream.toArray(Integer[]::new);
        // 2.转换为Collection
        List<Integer> list = stream.collect(Collectors.toList());
        // 3.转换为特定类型Collection
        ArrayList<Integer> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
//        // 4.转换为基础类型，此处选join
        Stream<String> stream2 = Stream.of("a", "b", "c");
        final String joiningStr = stream2.collect(Collectors.joining());
        System.out.println(joiningStr);
    }



    public static void main(String[] args) {
        StreamDemo demo = new StreamDemo();
        demo.transferStream();

    }
}
