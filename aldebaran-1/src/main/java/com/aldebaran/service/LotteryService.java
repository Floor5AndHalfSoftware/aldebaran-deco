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
public class LotteryService
{
  @Autowired
  com.aldebaran.service.HttpService http;
  @Autowired
  com.aldebaran.service.CryptoService cryptoService;
  
  public JsonNode lotteryTickets(String usertoken, String sessiontoken, String datefrom, String dateto) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();

    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSportsBook() + this.cryptoService.getSecretSharedSportsBook() + usertoken + sessiontoken + datefrom);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", datefrom);
     mb.add("to", dateto);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("lottery/list/", this.http.getUrlGamesFrontend(), mb.map()));
  }



  
  public JsonNode lotteryPayPrizeTickets(String usertoken, String sessiontoken, String ticketId) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();

    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSportsBook() + this.cryptoService.getSecretSharedSportsBook() + usertoken + sessiontoken);
     mb.add("ticketid", ticketId);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("lottery/payprize/list/", this.http.getUrlGamesFrontend(), mb.map()));
  }
}


