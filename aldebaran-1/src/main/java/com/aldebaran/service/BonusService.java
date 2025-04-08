package com.aldebaran.service;

import com.aldebaran.util.MapBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






@Service
public class BonusService
{
  @Autowired
  com.aldebaran.service.HttpService http;
  @Autowired
  com.aldebaran.service.CryptoService cryptoService;
  
  public JsonNode list(String usertoken, String sessiontoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedBonus() + this.cryptoService.getSecretSharedBonus() + usertoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bonus/list/", this.http.getUrlReportsFrontend(), mb.map()));
  }


  
  public JsonNode redeem(String usertoken, String sessiontoken, String promoid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedBonus() + this.cryptoService.getSecretSharedBonus() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("promoid", promoid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bonus/redeem/", this.http.getUrlCoreFrontend(), mb.map()));
  }

  
  public JsonNode grantAmountBonus(String usertoken, String sessiontoken, String promoid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedBonus() + this.cryptoService.getSecretSharedBonus() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("promoid", promoid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bonus/grant/bonusAmount", this.http.getUrlCoreFrontend(), mb.map()));
  }


  
  public JsonNode close(String usertoken, String sessiontoken, String promoid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedBonus() + this.cryptoService.getSecretSharedBonus() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("promoid", promoid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bonus/close/", this.http.getUrlCoreFrontend(), mb.map()));
  }
}


