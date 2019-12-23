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

    /**
     * findFirst操作是一个terminal兼short-circuiting操作
     */
   private void findFirstDemo() {
       Stream<Integer> integerStream = Stream.of(1, 5, 7, 9, 10);
       integerStream.findFirst().ifPresent(System.out::println);
   }

    /**
     * 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），
     * 和前面 Stream 的第一个、第二个、第 n 个元素组合。<br/>
     * 从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。<br/>
     * 例如 Stream 的 sum 就相当于:<br/>
     * <pre>
     * {@code Integer sum = integers.reduce(0, (a, b) -> a+b);}
     * // 或
     * {@code Integer sum = integers.reduce(0, Integer::sum);}
     * </pre>
     * 也有没有起始值(identity)的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。<br/>
     */
   private void reduceDemo() {
       //1.求和
       Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 7);
       Integer sum = stream1.reduce(0, Integer::sum);
       //2.字符串连接
       Stream<String> stream2 = Stream.of("Am ", "I ", "human ", "?");
       String concatStr = stream2.reduce("", String::concat);
       //3.求最小值
       Stream<Integer> stream3 = Stream.of(-1, 2, 3, 5, 7);
       Integer min = stream3.reduce(1, Integer::min);
   }


    public static void main(String[] args) {
        OperateStreamDemo demo = new OperateStreamDemo();
        demo.reduceDemo();
    }


}
