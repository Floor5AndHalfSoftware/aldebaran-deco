package com.aldebaran.controller;

import com.aldebaran.service.LotteryService;
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
public class LotteryController
{
  @Autowired
  LotteryService lotteryService;
  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/lottery/list"})
  public Object lotteryList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "datefrom", required = true) String datefrom, @RequestParam(value = "dateto", required = true) String dateto, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
    }
     return this.lotteryService.lotteryTickets(usertoken, sessiontoken, datefrom, dateto);
  }





  
  @RolesAllowed({"authenticated"})
  @PostMapping({"/lottery/payprize/list"})
  public Object lotteryPayPrizeList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "ticketId", required = true) String ticketId, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
    }
     return this.lotteryService.lotteryPayPrizeTickets(usertoken, sessiontoken, ticketId);
  }
}


