package com.wushiyii.messy.common.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.wushiyii.messy.common.dict.GuavaCacheEnum;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
public abstract class BaseGuavaCache<K, V> {

    /**
     * 本地全部缓存
     */
    private static final Map<String, LoadingCache> localCacheMap = Maps.newConcurrentMap();

    /**
     * 缓存线程池
     */
    @Resource
    private ListeningExecutorService service;

    /**
     * 初始化缓存
     *
     * @param cache
     * @param refreshSeconds
     * @return
     */
    protected LoadingCache<K, V> fetchLoadingCache(GuavaCacheEnum cache, long refreshSeconds) {
        LoadingCache<K, V> localCache = localCacheMap.get(cache.name());
        if (Objects.nonNull(localCache)) {
            return localCache;
        }
        localCache = CacheBuilder.newBuilder()
                //设置并发级别为10, 并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(10)
                //刷新时间
                .refreshAfterWrite(refreshSeconds, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为128
                .initialCapacity(128)
                //设置缓存数量上限, Map的最大容量
                .maximumSize(10000)
                //实现自动加载
                .build(new CacheLoader<K, V>() {
                    @Override
                    public V load(final K key) {
                        return loadFromDb(key);
                    }

                    @Override
                    public ListenableFuture<V> reload(K key, V oldValue) {
                        return service.submit(() -> {
                            try {
                                return loadFromDb(key);
                            } catch (Exception e) {
                                log.error("guava reload error.", e);
                                return oldValue;
                            }
                        });
                    }
                });
        localCacheMap.put(cache.name(), localCache);
        return localCache;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    protected abstract V getFromCache(K key);

    /**
     * 加载数据
     *
     * @param key 内部key
     * @return
     */
    protected abstract V loadFromDb(K key);

}
