package com.wushiyii.messy.grammar.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author who
 * 常见操作类型如下
 * <pre>Intermediate</pre>: map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 * <pre>Terminal</pre>: forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 * <pre>Short-circuiting</pre>: anymatch、 allmatch、 noneMatch、 findFirst、 findAny、 limit
 *
 */
public class OperateStreamDemo {

    /**
     * map操作 单输入单输出
     */
    private void mapDemo() {
        Stream<String> stream = Stream.of("who", "is", "your", "dad");
        //在map里可以使用方法引用
        List<String> upperCaseList = stream.map(String::toUpperCase).collect(Collectors.toList());
        //也可以使用指定操作
        List<String> upperCaseList2 = stream.map( x -> x.toUpperCase()).collect(Collectors.toList());
        upperCaseList.forEach(System.out::println);
        upperCaseList2.forEach(System.out::println);

        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        List<Integer> integerList = integerStream.map(x -> x * x).collect(Collectors.toList());
        integerList.forEach(System.out::println);
    }

    /**
     * 多输入，单输出（把输入的底层元素全部抽出放在一起）
     */
   private void flatMapDemo() {
       Stream<List<Integer>> multiStream = Stream.of(Arrays.asList(100, 200, 300), Arrays.asList(500, 600, 700), Arrays.asList(800, 900, 1000));
       int sum = multiStream.flatMap(x -> x.stream()).mapToInt(x -> x).sum();
       System.out.println(sum);
   }

    /**
     * filter过滤操作
     */
   private void filterDemo() {
       //过滤偶数并打印
       Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
       Integer[] integers = integerStream.filter(x -> x % 2 == 0).toArray(Integer[]::new);
       System.out.println(Arrays.toString(integers));
   }

    /**
     * foreach操作(foreach是consumer，操作后流会被消费，不可再使用；如需要重复使用，可以使用peek)
     */
   private void foreachDemo() {
       Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
       integerStream.forEach(System.out::println);

       Stream.of("one", "two", "three", "four")
               .peek(e -> System.out.println("Before value: " + e))
               .map(String::toUpperCase)
               .peek(e -> System.out.println("After value: " + e));


   }

    public static void main(String[] args) {
        OperateStreamDemo demo = new OperateStreamDemo();
        demo.foreachDemo();
    }


}
