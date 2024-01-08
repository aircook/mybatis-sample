package com.tistory.aircook.mybatis.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * CacheManager 구성
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        //ConcurrentMapCacheManager
        //메모리 상에 캐시 저장, ConcurrentHashMap 기반 캐시 구현체를 의미합니다.
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        //null 값을 캐싱하려고 하면 에러가 발생
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(List.of("memoCache"));
        return cacheManager;
    }
}
