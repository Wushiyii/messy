package com.wushiyii.messy.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LocalCacheUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalCacheUtil.class);

    private static Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(2000).expireAfterAccess(2, TimeUnit.SECONDS).build();

    public static<K,V> void put(K k, V v) {
        try {
            cache.put(k, v);
        }catch (Throwable t) {
            LogUtil.error(logger, t, "缓存异常");
        }
    }

    @SuppressWarnings("unchecked")
    public static  <K,V> V getMinutesCache(K k) {
        Object val = null;
        try {
            val = cache.getIfPresent(k);
        } catch (Throwable t) {
            LogUtil.error(logger, t, "缓存异常");
        }
        return (V) val;
    }

    public static void main(String[] args) throws InterruptedException {

        put("aaa", "aaa");
        Object aaa = getMinutesCache("aaa");
        System.out.println(aaa);

        Thread.sleep(2100);
        aaa = getMinutesCache("aaa");
        System.out.println(aaa);

    }
}
