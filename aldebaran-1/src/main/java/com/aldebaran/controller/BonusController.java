package com.aldebaran.controller;

import com.aldebaran.service.BonusService;
import com.aldebaran.service.HttpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;










@RestController
public class BonusController
{
  @Autowired
  HttpService http;
  @Autowired
  BonusService bonusService;
  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bonus/list"})
  public Object bonusList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.bonusService.list(usertoken, sessiontoken);
  }






  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bonus/redeem"})
  public Object bonusRedeem(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "promoid", required = true) String promoid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.bonusService.redeem(usertoken, sessiontoken, promoid);
  }






  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bonus/close"})
  public Object closeBonus(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "promoid", required = true) String promoid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.bonusService.close(usertoken, sessiontoken, promoid);
  }




  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bonus/grant/amount"})
  public Object grantAmountBonus(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "promoid", required = true) String promoid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.bonusService.grantAmountBonus(usertoken, sessiontoken, promoid);
  }
}


