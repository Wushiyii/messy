package com.wushiyii.messy.common.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BaseGuavaCacheConfig {

    @Bean("guavaCacheExecutor")
    public ThreadPoolTaskExecutor guavaCacheExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("guavaCachePool");
//        executor.setTaskDecorator(new MdcTaskDecorator());
        return executor;
    }

    @Bean
    public ListeningExecutorService listeningExecutorService(@Qualifier(value = "guavaCacheExecutor") ThreadPoolTaskExecutor executor) {
        return MoreExecutors.listeningDecorator(executor.getThreadPoolExecutor());
    }

}
