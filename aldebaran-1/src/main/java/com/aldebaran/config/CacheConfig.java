package com.aldebaran.config;

import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfig
{
  @Bean
  CacheManager cacheManager() {
     SimpleCacheManager cacheManager = new SimpleCacheManager();
     long ttl = 3600000L;
     cacheManager.setCaches(Arrays.asList(new com.aldebaran.config.TtlCache[] {
             createCache("gameListCache", ttl), 
             createCache("topGamesRoundsCache", ttl), 
             createCache("topGamesWinCache", ttl), 
             createCache("gameCategoriesCache", ttl), 
             createCache("cmsTextCache", ttl), 
             createCache("cmsFileCache", ttl)
          }));
     return (CacheManager)cacheManager;
  }
  
  private com.aldebaran.config.TtlCache createCache(String name, long ttl) {
     return new com.aldebaran.config.TtlCache(name, ttl);
  }
}


