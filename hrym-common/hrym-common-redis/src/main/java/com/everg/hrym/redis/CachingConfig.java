package com.everg.hrym.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
//        redisCacheManager.setDefaultExpiration(3);
//        return redisCacheManager;
//    }

    @Bean
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

    private static class RedisCacheErrorHandler extends SimpleCacheErrorHandler {

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
//            log.error("handleCacheGetError key = {}, value = {}", key, cache);
//            log.error("cache get error", exception);
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
//            log.error("handleCachePutError key = {}, value = {}", key, cache);
//            log.error("cache put error", exception);
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
//            log.error("handleCacheEvictError key = {}, value = {}", key, cache);
//            log.error("cache evict error", exception);
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
//            log.error("handleCacheClearError value = {}", cache);
//            log.error("cache clear error", exception);
        }
    }
}