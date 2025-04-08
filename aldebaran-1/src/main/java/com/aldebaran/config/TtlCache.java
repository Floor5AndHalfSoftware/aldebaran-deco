package com.aldebaran.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

public class TtlCache
  extends ConcurrentMapCache {
   private final Map<Object, Long> expirationMap = new ConcurrentHashMap<>();
  private final long ttl;
   private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
  
  public TtlCache(String name, long ttl) {
     super(name);
     this.ttl = ttl;
     startEvictionScheduler();
  }

  
  public void put(Object key, Object value) {
     super.put(key, value);
     this.expirationMap.put(key, System.currentTimeMillis() + this.ttl);
  }

  
  public Cache.ValueWrapper get(Object key) {
     if (isExpired(key)) {
       evict(key);
       return null;
    } 
     return super.get(key);
  }

  
  public void evict(Object key) {
     super.evict(key);
     this.expirationMap.remove(key);
  }
  
  private boolean isExpired(Object key) {
     Long expirationTime = this.expirationMap.get(key);
     return (expirationTime != null && System.currentTimeMillis() > expirationTime);
  }
  
  private void startEvictionScheduler() {
     this.executorService.scheduleAtFixedRate(this::evictExpiredEntries, this.ttl, this.ttl, TimeUnit.MILLISECONDS);
  }
  
  private void evictExpiredEntries() {
     this.expirationMap.keySet().forEach(key -> {
          if (isExpired(key))
            evict(key); 
        });
  }
}


