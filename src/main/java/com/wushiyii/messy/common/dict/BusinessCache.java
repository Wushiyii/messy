package com.wushiyii.messy.common.dict;


import com.google.common.cache.LoadingCache;
import com.wushiyii.messy.common.utils.BaseGuavaCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class BusinessCache extends BaseGuavaCache<String, Object> {

    protected LoadingCache<String, Object> localCacheMap;

//    此处为需要获取资源的dao
//    @Resource
//    private BusinessRepository businessRepository;

    @PostConstruct
    public void init() {
        log.info("BusinessCache init expire time second {}", 300);
        localCacheMap = fetchLoadingCache(GuavaCacheEnum.BUSINESS_NAME, 300);
    }

    @Override
    protected Object getFromCache(String key) {
        try {
            return localCacheMap.get(key);
        } catch (ExecutionException e) {
            log.error("BusinessCache fetch error.", e);
        }
        return loadFromDb(key);
    }

    @Override
    protected Object loadFromDb(String key) {
//        return businessRepository.selectByServiceCode(key);
        return new Object();
    }

}
