package com.gengxiankun.familycapitalpool.configure;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiankun.geng
 */
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, List<String>> notifyCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(10)
                .build();
    }

}
