package com.wushiyii.messy.grammar.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
//       Stream<List<Integer>> multiStream = Stream.of(Arrays.asList(100, 200, 300));
       List<Integer> integerList = Arrays.asList(100, 200, 300);
       int sum = integerList.stream().mapToInt(x -> x).sum();
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

    /**
     * limit 返回 Stream 的前面 n 个元素；
     * skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）。
     * 这两种操作都为short-circuiting
     */
   private void limitDemo() {
       Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 7);
       stream1.limit(2).forEach(System.out::println);

       Stream<Integer> stream2 = Stream.of(1, 2, 3, 5, 7);
       stream2.skip(3).forEach(System.out::println);
   }

    /**
     * sorted排序操作
     * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于：
     * 你可以首先对 Stream 进行各类 map、filter、limit、skip ，甚至 distinct 来减少元素数量后，再排序
     * 这能帮助程序明显缩短执行时间。
     */
   private void sortedDemo() {
        Stream<Integer> stream1 = Stream.of(5, 4, 2, 3, 1, 7);
        stream1.sorted().forEach(System.out::println);
   }

    /**
     * min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，
     * 但前者的性能会更好，为 O(n)，而 sorted 的成本是 O(n log n)。
     * 同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
     */
   private void maxMinDistinctDemo(){
       IntStream intStream1 = IntStream.of(5, 4, 2, 3, 1, 7);
       intStream1.max().ifPresent(System.out::println);

       IntStream intStream2 = IntStream.of(5, 4, 2, 3, 1, 7);
       intStream2.min().ifPresent(System.out::println);

       IntStream intStream3 = IntStream.of(1, 1, 1, 2, 2, 3);
       intStream3.distinct().forEach(System.out::println);
   }

    /**
     * Match：匹配操作
     * predicate:预测的规则
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     */
   private void matchDemo() {
       //判断是否全都匹配xxx
       Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 7);
       final boolean isAllPositive = stream1.allMatch(x -> x > 0);
       System.out.println(isAllPositive);

       //判断是否有任意一个匹配xxx
       Stream<Integer> stream2 = Stream.of(1, 2, 3, 5, 7);
       final boolean ifContainsOne = stream2.anyMatch(x -> x == 1);
       System.out.println(ifContainsOne);

       //判断是否所有都不匹配xxx
       Stream<Integer> stream3 = Stream.of(1, 2, 3, 5, 7);
       final boolean isNotContainsZero = stream3.noneMatch(x -> x == 0);
       System.out.println(isNotContainsZero);
   }


    public static void main(String[] args) {
        OperateStreamDemo demo = new OperateStreamDemo();
        demo.flatMapDemo();
    }


}
