package com.wushiyii.messy.common.utils;


import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * Eh = Enhance，对方法的第一个入参进行增强，较特殊且使用频率高，因此缩写为Eh。
 * 一般情况下，仅对第一个入参进行NullSafe处理。
 * </pre>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Eh {

    // region Enhance Collection

    public static <T> Optional<T> first(Collection<T> collection) {
        if (collection == null) {
            return Optional.empty();
        }
        return collection.stream().findFirst();
    }

    public static <T, R> R firstMap(Collection<T> collection, Function<T, R> mapper, R defaultValue) {
        return firstMap(collection, mapper).orElse(defaultValue);
    }

    public static <T, R> R firstMap2(Collection<T> collection, Function<T, R> mapper, Supplier<R> defaultValueSupplier) {
        return firstMap(collection, mapper).orElseGet(defaultValueSupplier);
    }

    public static <T, R> Optional<R> firstMap(Collection<T> collection, Function<T, R> mapper) {
        return first(collection).map(mapper);
    }

    public static <T, R> List<R> mapList(Collection<T> collection, Function<T, R> mapper) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T, R> Set<R> mapSet(Collection<T> collection, Function<T, R> mapper) {
        if (collection == null) {
            return new HashSet<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <T, R> List<R> mapDistinctList(Collection<T> collection, Function<T, R> mapper) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .map(mapper)
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T> List<T> filterList(Collection<T> collection, Predicate<? super T> predicate) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> collection, Function<T, K> keyMapper) {
        if (collection == null) {
            return new HashMap<>();
        }
        return collectUnSafe(collection, Collectors.groupingBy(keyMapper));
    }

    public static <T, K, D> Map<K, D> groupingBy(Collection<T> collection, Function<T, K> keyMapper, Collector<? super T, ?, D> downstream) {
        if (collection == null) {
            return new HashMap<>();
        }
        return collectUnSafe(collection, Collectors.groupingBy(keyMapper, downstream));
    }

    public static <T, K> Map<K, Long> groupingCountBy(Collection<T> collection, Function<T, K> keyMapper) {
        return groupingBy(collection, keyMapper, Collectors.counting());
    }

    public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyMapper) {
        return toMap(collection, keyMapper, v -> v);
    }

    /**
     * @see #toMap(Collection, Function, boolean)
     */
    public static <T, K> Map<K, T> toUniqueMap(Collection<T> collection, Function<T, K> keyMapper) {
        return toMap(collection, keyMapper, true);
    }

    /**
     * @param throwIfDuplicate 出现重复key时是否报错，为true时报错。
     */
    public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyMapper, boolean throwIfDuplicate) {
        if (throwIfDuplicate) {
            return toMap(collection, keyMapper, v -> v, (u, v) -> {
                throw new RuntimeException("Map Exception");
            });
        } else {
            return toMap(collection, keyMapper, v -> v, (o, n) -> n);
        }
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return toMap(collection, keyMapper, valueMapper, (o, n) -> n);
    }

    public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyMapper, BinaryOperator<T> mergeFunction) {
        return toMap(collection, keyMapper, v -> v, mergeFunction);
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection, Function<T, K> keyMapper,
                                            Function<T, V> valueMapper, BinaryOperator<V> mergeFunction) {
        if (collection == null) {
            return new HashMap<>();
        }
        return collectUnSafe(collection, Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    private static <T, R, A> R collectUnSafe(Collection<T> collection, Collector<T, A, R> collector) {
        return collection.stream().collect(collector);
    }

    // endregion

    // region Enhance Map

    public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
        return get(map, key).orElse(defaultValue);
    }

    public static <K, V> V get2(Map<K, V> map, K key, Supplier<V> defaultValueSupplier) {
        return get(map, key).orElseGet(defaultValueSupplier);
    }

    public static <K, V> String getString(Map<K, V> map, K key, V defaultValue) {
        return (String) get(map, key).orElse(defaultValue);
    }

    public static <K, V> String getString2(Map<K, V> map, K key, Supplier<V> defaultValueSupplier) {
        return (String) get(map, key).orElseGet(defaultValueSupplier);
    }

    public static <K, V> Optional<V> get(Map<K, V> map, K key) {
        if (map == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(map.get(key));
    }

    // endregion

    // region Enhance Stream

    public static <T, R> List<R> mapList(Stream<T> stream, Function<T, R> mapper) {
        if (stream == null) {
            return new ArrayList<>();
        }
        return stream
                .map(mapper)
                .collect(Collectors.toList());
    }

    // endregion

    // region Enhance Object

    public static <T> T nvl(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T nvl2(T object, Supplier<T> defaultValueSupplier) {
        return object != null ? object : defaultValueSupplier.get();
    }

    public static <T, R> R map(T object, Function<T, R> mapper, R defaultValue) {
        return map(object, mapper).orElse(defaultValue);
    }

    public static <T, R> R map2(T object, Function<T, R> mapper, Supplier<R> defaultValueSupplier) {
        return map(object, mapper).orElseGet(defaultValueSupplier);
    }

    public static <T, R> Optional<R> map(T object, Function<T, R> mapper) {
        return Optional.ofNullable(object).map(mapper);
    }

    public static <T, R1, R2> R2 map(T object, Function<T, R1> mapper1, Function<R1, R2> mapper2, R2 defaultValue) {
        return map(object, mapper1, mapper2).orElse(defaultValue);
    }

    public static <T, R1, R2> R2 map2(T object, Function<T, R1> mapper1, Function<R1, R2> mapper2, Supplier<R2> defaultValueSupplier) {
        return map(object, mapper1, mapper2).orElseGet(defaultValueSupplier);
    }

    public static <T, R1, R2> Optional<R2> map(T object, Function<T, R1> mapper1, Function<R1, R2> mapper2) {
        return map(object, mapper1).map(mapper2);
    }

    public static <T, R1, R2, R3> R3 map(
            T object,
            Function<T, R1> mapper1, Function<R1, R2> mapper2, Function<R2, R3> mapper3,
            R3 defaultValue) {
        return map(object, mapper1, mapper2, mapper3).orElse(defaultValue);
    }

    public static <T, R1, R2, R3> R3 map2(
            T object,
            Function<T, R1> mapper1, Function<R1, R2> mapper2, Function<R2, R3> mapper3,
            Supplier<R3> defaultValueSupplier) {
        return map(object, mapper1, mapper2, mapper3).orElseGet(defaultValueSupplier);
    }

    public static <T, R1, R2, R3> Optional<R3> map(T object, Function<T, R1> mapper1, Function<R1, R2> mapper2, Function<R2, R3> mapper3) {
        return map(object, mapper1, mapper2).map(mapper3);
    }

    // endregion
}