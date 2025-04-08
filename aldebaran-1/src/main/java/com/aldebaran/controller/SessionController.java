 package com.aldebaran.controller;
 
 import com.aldebaran.service.HttpService;
 import com.aldebaran.service.SessionService;
 import com.aldebaran.util.UserUtil;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonMappingException;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import javax.annotation.security.PermitAll;
 import javax.annotation.security.RolesAllowed;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.servlet.ModelAndView;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @RestController
 public class SessionController
 {
   @Autowired
   HttpService http;
   @Autowired
   SessionService sessionService;
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/session/open"})
   public Object openSession(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "gameid", required = true) Integer gameid, @RequestParam(value = "type", required = true) String type, @RequestParam(value = "amount", required = true) Double amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "promoid", required = false) String promoid, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
if (UserUtil.isUserSelfexcluded(request)) {
return UserUtil.messageUserSelfexcluded();
     }
return this.sessionService.open(usertoken, sessiontoken, gameid, type, amount, currency, promoid);
   }
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/session/close"})
   public Object closeSession(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "sessionid", required = true) String sessionid, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
if (UserUtil.isUserSelfexcluded(request)) {
return UserUtil.messageUserSelfexcluded();
     }
return this.sessionService.close(usertoken, sessiontoken, sessionid);
   }
 
 
 
 
 
   
   @PermitAll
   @GetMapping({"/session/lobby/close"})
   public ModelAndView closeLobbySession(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "sessionid", required = true) String sessionid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
this.sessionService.close(usertoken, sessiontoken, sessionid);
return new ModelAndView("redirect:" + this.http.getServerUrl() + "#/games");
   }
 
 
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/session/list"})
   public Object sessionList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "gameid", required = false) Integer gameid, @RequestParam(value = "state", required = false) String state, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
if (UserUtil.isUserSelfexcluded(request)) {
return UserUtil.messageUserSelfexcluded();
     }
return this.sessionService.list(usertoken, sessiontoken, gameid, state, from, to);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/session/detail"})
   public Object sessionDetail(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "sessionid", required = true) String sessionid, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     return this.sessionService.detail(usertoken, sessiontoken, sessionid);
   }
 }


