 package com.aldebaran.controller;
 
 import com.aldebaran.service.HttpService;
 import com.aldebaran.service.PsdService;
 import com.aldebaran.service.UserService;
 import com.aldebaran.util.MapBuilder;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonNode;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import javax.annotation.security.RolesAllowed;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 
 
 
 
 
 
 
 
 
 
 
 
 @RestController
 public class PsdController
 {
   @Autowired
   HttpService http;
   @Autowired
   PsdService psdService;
   @Autowired
   UserService userService;
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/withdraw/psd/list"})
   public Object withdrawPsdList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
return this.psdService.list(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/withdraw/bank/list"})
   public Object withdrawBankList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
return this.psdService.bankTransferList(usertoken, sessiontoken, from, to);
   }
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/bank/list"})
   public Object bankList(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
return this.psdService.bankList();
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/withdraw/psd/reserve"})
   public Object withdrawPsdReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) Integer amount, @RequestParam(value = "currency", required = true) String currency, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
MapBuilder<String, Object> mb = new MapBuilder();
JsonNode user = this.userService.user(usertoken, sessiontoken);
if (this.userService.isMetadataProperty(this.userService.getMetadataArray(user.get("userid").asInt()), "persona_id")) {
JsonNode result = this.psdService.reserve(usertoken, sessiontoken, amount, currency);
if (result.has("result") && result.get("result").asInt() != 0) {
mb.add("messageKo", result.get("description").asText());
mb.add("result", result.get("result").asInt());
return mb.map();
       } 
return result;
     } 
return null;
   }
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/withdraw/bank/reserve"})
   public Object withdrawBankReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) Integer amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "bankId", required = true) Integer bankId, @RequestParam(value = "type", required = true) String type, @RequestParam(value = "ctaNumber", required = true) String ctaNumber, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
MapBuilder<String, Object> mb = new MapBuilder();
JsonNode user = this.userService.user(usertoken, sessiontoken);
if (this.userService.isMetadataProperty(this.userService.getMetadataArray(user.get("userid").asInt()), "persona_id")) {
       JsonNode result = this.psdService.bankReserve(usertoken, sessiontoken, amount, currency, bankId, type, ctaNumber);
       
       if (result.has("result") && result.get("result").asInt() != 0) {
         mb.add("messageKo", result.get("description").asText());
         mb.add("result", result.get("result").asInt());
         return mb.map();
       } 
       return result;
     } 
     return null;
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/psd/redeem"})
   public Object depositPsdRedeem(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "retiroid", required = true) String retiroid, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     JsonNode user = this.userService.user(usertoken, sessiontoken);
     if (user.has("result") && user.get("result").asInt() == 0 && 
       this.userService.isMetadataProperty(this.userService.getMetadataArray(user.get("userid").asInt()), "psd_transfer_funds"))
     {
       return this.psdService.redeem(usertoken, sessiontoken, retiroid);
     }
     return null;
   }
 }


