 package com.aldebaran.service;
 
 import com.aldebaran.util.MapBuilder;
 import com.aldebaran.util.MathUtil;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonMappingException;
 import com.fasterxml.jackson.databind.JsonNode;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 
 
 
 
 
 
 
 
 
 
 @Service
 public class SessionService
 {
   @Autowired
   com.aldebaran.service.HttpService http;
   @Autowired
   com.aldebaran.service.CryptoService cryptoService;
   
   public JsonNode open(String usertoken, String sessiontoken, Integer gameid, String type, Double amount, String currency, String promoid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
String amountCent = "" + MathUtil.eurocentInt("" + amount);
MapBuilder<String, String> mb = new MapBuilder();
     
String promoidP = "";
if (promoid != null) promoidP = promoid;
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + usertoken + sessiontoken + gameid + type + amountCent + currency);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("gameid", gameid.toString());
mb.add("type", type);
mb.add("amount", amountCent);
mb.add("currency", currency);
mb.add("promoid", promoidP);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("session/open", this.http.getUrlGamesBackend(), mb.map()));
   }
 
 
 
   
   public JsonNode close(String usertoken, String sessiontoken, String sessionid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + usertoken + sessiontoken);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("sessionid", sessionid);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("session/close", this.http.getUrlGamesBackend(), mb.map()));
   }
 
 
 
 
 
 
   
   public JsonNode list(String usertoken, String sessiontoken, Integer gameid, String state, String from, String to) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
String gameidP = "";
String stateP = "";
if (gameid != null) gameidP = "" + gameid; 
if (state != null) stateP = state; 
String fromP = "";
if (from != null) fromP = from; 
String toP = "";
if (to != null) toP = to;
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + usertoken + sessiontoken + fromP + toP + gameidP);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("from", fromP);
mb.add("to", toP);
mb.add("gameid", gameidP);
mb.add("state", stateP);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("session/list", this.http.getUrlGamesFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode detail(String usertoken, String sessiontoken, String sessionid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("sessionid", sessionid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("session/detail", this.http.getUrlGamesFrontend(), mb.map()));
   }
 }


