package com.aldebaran.controller;

import com.aldebaran.service.SportsBookService;
import com.aldebaran.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;













@RestController
public class SportsBookController
{
  @Autowired
  SportsBookService sportsBookService;
  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bets/list"})
  public Object betsList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "datefrom", required = true) String datefrom, @RequestParam(value = "dateto", required = true) String dateto, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "tsType", required = false) String tsType, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
    }
     return this.sportsBookService.bets(usertoken, sessiontoken, datefrom, dateto, status, tsType);
  }



  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bets/detail"})
  public Object betsDetail(@RequestParam(value = "betid", required = true) String betid, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
    }
     return this.sportsBookService.betsDetail(betid);
  }


  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/bets/metadata/list"})
  public Object betsMetadata(@RequestParam(value = "betid", required = true) String betid, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
    }
     return this.sportsBookService.betsMetadata(betid);
  }
}


