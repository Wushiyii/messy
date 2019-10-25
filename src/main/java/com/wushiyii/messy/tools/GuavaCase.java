package com.wushiyii.messy.tools;

import com.google.common.base.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.*;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCase {

    private static void collection() {
        //集合类
        List<String> list = Lists.newArrayList();
        Set<Object> set = Sets.newHashSet();
        Map<Object, Object> map = Maps.newHashMap();
        //不可变容器
        ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3);
        ImmutableSet<Integer> immutableSet = ImmutableSet.of(1, 2, 3, 3);
        ImmutableMap<String, String> immutableMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        Map<String, List<Integer>> mapInteger = new HashMap<>();//value为容器时组装很麻烦，可以使用ArrayListMultimap
        List<Integer> integerList = Lists.newArrayList();
        integerList.add(10);
        integerList.add(20);
        mapInteger.put("k1",integerList);

        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("k1",10);
        multimap.put("k1",20);
        System.out.println(mapInteger);
        System.out.println(multimap);

        List<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        System.out.println(Joiner.on(",").join(strings));

        Map<String, Integer> map1 = Maps.newHashMap();
        map1.put("aaa", 111);
        map1.put("bbb", 222);
        System.out.println(Joiner.on(",").withKeyValueSeparator("==").join(map1));//aaa==111,bbb==222

        //去除连接符
        System.out.println(Splitter.on("-").splitToList("a-b-c-d-e-f-g"));
        System.out.println(Splitter.on("-").omitEmptyStrings().trimResults().splitToList("a - b - c   -d-  e-f-  g"));

        ImmutableList<String> immutableList1 = ImmutableList.of("aaa", "bbb", "ccc", "ddd");
        Iterable<String> ite = Iterables.filter(immutableList1, Predicates.or(Predicates.equalTo("ccc")));
        System.out.println(ite);

    }

    /*
        校验条件
     */
    private static void validate() {
        int count = 1;
        Preconditions.checkArgument( count > 5, "数字必须大于5");
    }

    private static void calcCostTime() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1000);
        System.out.println(stopwatch);
    }

    /*
        文件
     */
    private static void fileOp() {
        File file = new File("/Users/qudian/Desktop/test.txt");
        List<String> content = null;
        try {
            content = Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);

        //读取resource目录
        URL url = Resources.getResource("application.properties");
        System.out.println(url);

    }

    /*
        缓存使用
     */
    private static void cache() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(5, TimeUnit.SECONDS).build();
        cache.put("k1", "v1");
        System.out.println("取值 : " + cache.get("k1", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }));
        Thread.sleep(6000);
        System.out.println("取值 : " + cache.get("k1", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }));
    }



    public static void main(String[] args) throws Exception {
        cache();

    }
}
