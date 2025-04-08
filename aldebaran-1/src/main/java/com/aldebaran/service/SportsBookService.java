package com.aldebaran.service;

import com.aldebaran.util.MapBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;







@Service
public class SportsBookService
{
  @Autowired
  com.aldebaran.service.HttpService http;
  @Autowired
  com.aldebaran.service.CryptoService cryptoService;
  
  public JsonNode bets(String usertoken, String sessiontoken, String datefrom, String dateto, String status, String tsType) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String statusP = "";
     if (status != null) statusP = status; 
     String tsTypeP = "";
     if (tsType != null) tsTypeP = tsType;

    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSportsBook() + this.cryptoService.getSecretSharedSportsBook() + usertoken + sessiontoken + datefrom + dateto + statusP);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", datefrom);
     mb.add("to", dateto);
     mb.add("status", statusP);
     mb.add("tsType", tsTypeP);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bets/list/", this.http.getUrlGamesFrontend(), mb.map()));
  }


  
  public JsonNode betsDetail(String betid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSportsBook() + this.cryptoService.getSecretSharedSportsBook());
     mb.add("betid", betid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bets/detail/", this.http.getUrlGamesBackend(), mb.map()));
  }


  
  public JsonNode betsMetadata(String betid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSportsBook() + this.cryptoService.getSecretSharedSportsBook());
     mb.add("betid", betid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("bets/metadata/list", this.http.getUrlGamesBackend(), mb.map()));
  }
}


