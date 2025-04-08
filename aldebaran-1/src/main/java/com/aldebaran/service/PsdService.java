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
 public class PsdService
 {
   @Autowired
   com.aldebaran.service.HttpService http;
   @Autowired
   com.aldebaran.service.CryptoService cryptoService;
   
   public JsonNode list(String usertoken, String sessiontoken, String from, String to) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
 
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("from", from);
mb.add("to", to);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("withdraw/psd/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode bankTransferList(String usertoken, String sessiontoken, String from, String to) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
 
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("from", from);
mb.add("to", to);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("withdraw/bank/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
   
   public JsonNode bankList() throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String enabled = "1";
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
mb.add("enabled", enabled);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("bank/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode reserve(String usertoken, String sessiontoken, Integer amount, String currency) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
 
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("amount", "" + amount);
mb.add("currency", currency);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("withdraw/psd/reserve/", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode bankReserve(String usertoken, String sessiontoken, Integer amount, String currency, Integer bankId, String type, String ctaNumber) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount + currency + type + bankId);
     
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("amount", "" + amount);
mb.add("currency", currency);
mb.add("type", type);
mb.add("bankId", "" + bankId);
mb.add("ctaNumber", ctaNumber);
mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("withdraw/bank/reserve/", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode redeem(String usertoken, String sessiontoken, String retiroid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
 
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("retiroid", retiroid);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("deposit/psd/redeem", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 }


