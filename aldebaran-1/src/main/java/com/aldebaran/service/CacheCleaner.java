package com.aldebaran.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;



@Service
public class CacheCleaner
{
  @Autowired
  com.aldebaran.service.CryptoService cryptoService;
  @Autowired
  CacheManager cacheManager;
   Logger logger = LoggerFactory.getLogger(com.aldebaran.service.CacheCleaner.class);

  
  public void cleanCache(String type, String timestamp, String hash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
     this.logger.info("cleanCache called with [" + type + ", " + timestamp + ", " + hash + "]");
     String localHash = this.cryptoService.digest(this.cryptoService.getSecretSharedOperation() + this.cryptoService.getSecretSharedOperation() + type);
     Long currentTime = System.currentTimeMillis();
     Long requestTime = Long.parseLong(timestamp);
     this.logger.info("cleanCache localHash: " + localHash + ", currentTime: " + currentTime);
    
     if (localHash.equals(hash) && currentTime - requestTime < 20000L) {
       if (type.equals("cms")) {
         cmsCacheCleaner();
      }
       if (type.equals("games")) {
         gamesCacheCleaner();
      }
    } else {
       throw new RuntimeException("Invalid request");
    } 
  }
  
  private void gamesCacheCleaner() {
     this.cacheManager.getCache("gameListCache").clear();
     this.cacheManager.getCache("topGamesRoundsCache").clear();
     this.cacheManager.getCache("topGamesWinCache").clear();
     this.cacheManager.getCache("gameCategoriesCache").clear();
     this.logger.info("gamesCacheCleaner called");
  }
  
  private void cmsCacheCleaner() {
     this.cacheManager.getCache("cmsTextCache").clear();
     this.cacheManager.getCache("cmsFileCache").clear();
     this.logger.info("cmsCacheCleaner called");
  }
}


