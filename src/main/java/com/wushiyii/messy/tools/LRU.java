package com.wushiyii.messy.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @author wgq
 * @date 2020/8/20 11:49 上午
 */
@Component
public class LRU<K, V> implements InitializingBean, BeanPostProcessor {

    private LinkedHashMap<K, V> linkedHashMap;
    private static final int DEFAULT_CAPACITY = 5;
    private int size;


    public LRU() {
        this.linkedHashMap = new LinkedHashMap<>(DEFAULT_CAPACITY);
        this.size = DEFAULT_CAPACITY;
    }

    public V get(K k) {
        if (linkedHashMap.containsKey(k)) {
            V value = linkedHashMap.get(k);
            linkedHashMap.remove(k);
            linkedHashMap.put(k, value);
        }
        return linkedHashMap.get(k);
    }

    public void put(K k, V v) {
        // refresh
        if (linkedHashMap.containsKey(k)) {
            linkedHashMap.remove(k);
        }

        if (this.size == linkedHashMap.size()) {
            final Iterator<K> iterator = linkedHashMap.keySet().iterator();
            linkedHashMap.remove(iterator.next());
        }
        linkedHashMap.put(k, v);
    }

    public int size() {
        return size;
    }


    public static void main(String[] args) {
        LRU<String, String > lru = new LRU<>();
        lru.put("111", "111");
        lru.put("222", "222");
        lru.put("333", "333");
        lru.put("444", "444");

        System.out.println(lru.linkedHashMap);
    }


    @PostConstruct
    public void postConstructInit() {
        System.out.println("LRU execute postConstructInit()");
    }

    @Bean("init")
    public void generateLRU() {

    }

    public void init() {
        System.out.println("LRU execute init()");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LRU execute InitializingBean");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LRU execute postProcess.before(), beanName:" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("LRU execute postProcess.after(), beanName:" + beanName);
        return bean;

    }
}
