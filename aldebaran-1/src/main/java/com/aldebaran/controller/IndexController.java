 package com.aldebaran.controller;
 
 import com.aldebaran.auth.APrincipal;
 import com.aldebaran.model.Game;
 import com.aldebaran.service.CacheCleaner;
 import com.aldebaran.service.GamesService;
 import com.aldebaran.service.HttpService;
 import com.aldebaran.service.MtpuService;
 import com.aldebaran.util.UserUtil;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import java.util.List;
 import java.util.Random;
 import java.util.stream.Collectors;
 import javax.annotation.security.PermitAll;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.servlet.ModelAndView;
 
 
 
 
 
 
 
 
 
 
 @Controller
 public class IndexController
 {
   public static final String INDEX = "index";
   public static final String BANNERS = "banners";
   public static final String GAME_LAUNCHER = "gamelauncher";
   public static final String GAME_LAUNCHER_1 = "gamelauncher1";
   public static final String BARTOP = "bartop";
   public static final String LIW = "liw";
   public static final String SIGNIN = "sign-in";
   public static final String THANKYOU = "thankyou";
   public static final String WRONG_LOCATION = "wrong-location";
   @Autowired
   MtpuService mtpu;
   @Autowired
   HttpService http;
   @Autowired
   GamesService gamesService;
   @Autowired
   CacheCleaner cacheCleaner;
   
   @PermitAll
   @RequestMapping({"/"})
   public ModelAndView index(HttpServletRequest request) {
APrincipal ap = UserUtil.getUser(request);
     
ModelAndView mav = new ModelAndView("index");
mav.addObject("serverUrl", this.http.getServerUrl());
mav.addObject("jsDebug", this.http.getJsDebug());
mav.addObject("underAge", this.http.getUnderAge());
mav.addObject("betMethod", this.http.getBetMethod());
mav.addObject("currency", this.http.getCurrency());
mav.addObject("locate", this.http.getLocate());
mav.addObject("sportsbookWalletcode", this.http.getSportsbookWalletcode());
mav.addObject("sportsbookJs", this.http.getSportsbookJs());
mav.addObject("sportsbookJsInit", this.http.getSportsbookJsInit());
mav.addObject("operator", this.http.getOperator());
mav.addObject("gsNewLauncher", this.http.getGsNewLauncher());
mav.addObject("gsNewLauncher1", this.http.getGsNewLauncher1());
mav.addObject("mtpuUrl", this.mtpu.getUrl());
mav.addObject("mtpuUrlLaunch", this.mtpu.getUrlLaunch());
mav.addObject("urlSso", this.http.getUrlSso());
mav.addObject("loteriaUrlDetail", this.http.getLoteriaUrlDetail());
mav.addObject("clearsaleClientId", this.http.getClearsaleClientId());
mav.addObject("smarticoPublicBrandKey", this.http.getSmarticoPublicBrandKey());
mav.addObject("smarticoPublicLabelKey", this.http.getSmarticoPublicLabelKey());
     
if (ap != null) {
mav.addObject("principal", ap);
mav.addObject("urlSso", 
this.http.getUrlSso() + "?usertoken=" + this.http.getUrlSso() + "&sessiontoken=" + ap.getUserId());
     } 
return mav;
   }
 
 
 
 
 
   
   @PermitAll
   @RequestMapping({"/banners"})
   public ModelAndView banners(@RequestParam(value = "openGame", required = false) String openGame, @RequestParam(value = "heightGame", required = false) String heightGame, @RequestParam(value = "position", required = true) String position, @RequestParam(value = "language", required = true) String language, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
ModelAndView mav = new ModelAndView("banners");
mav.addObject("serverUrl", this.http.getServerUrl());
mav.addObject("openGame", openGame);
     mav.addObject("heightGame", heightGame);
     mav.addObject("position", position);
     mav.addObject("language", language);
     
     APrincipal ap = UserUtil.getUser(request);
     if (ap != null) {
       List<Game> games = this.gamesService.filterGameList(this.gamesService.getGameList(), UserUtil.getUserId(request), "MINIGAME");
       
       if (games != null && !games.isEmpty()) {
         List<Integer> miniGames = (List<Integer>)games.stream().map(Game::getGameID).collect(Collectors.toList());
         int randIndex = new Random().nextInt(miniGames.size());
         mav.addObject("gameid", miniGames.get(randIndex));
       } 
       mav.addObject("usertoken", ap.getUserId());
       mav.addObject("sessiontoken", ap.getSessionId());
       mav.addObject("currency", this.http.getCurrency());
       mav.addObject("mtpuUrlLaunch", this.mtpu.getUrlLaunch());
       mav.addObject("operator", this.http.getOperator());
     } 
     return mav;
   }
 
 
 
 
 
   
   @PermitAll
   @RequestMapping({"/gamelauncher"})
   public ModelAndView gamelauncher(@RequestParam(value = "link", required = true) String link, @RequestParam(value = "usertoken", required = false) String usertoken, @RequestParam(value = "sessiontoken", required = false) String sessiontoken, @RequestParam(value = "sessionid", required = false) String sessionid, @RequestParam(value = "lang", required = false) String lang, HttpServletRequest request) {
     ModelAndView mav = new ModelAndView("gamelauncher");
     mav.addObject("link", link);
     mav.addObject("lang", lang);
     if (usertoken != null) {
       mav.addObject("urllobby", this.http.getServerUrl() + "session/lobby/close?usertoken=" + this.http.getServerUrl() + "&sessiontoken=" + usertoken + "&sessionid=" + sessiontoken);
     } else {
       
       mav.addObject("urllobby", this.http.getServerUrl() + "#/games");
     }  return mav;
   }
 
 
 
 
 
   
   @PermitAll
   @RequestMapping({"/gamelauncher1"})
   public ModelAndView gamelauncher1(@RequestParam(value = "link", required = true) String link, @RequestParam(value = "usertoken", required = false) String usertoken, @RequestParam(value = "sessiontoken", required = false) String sessiontoken, @RequestParam(value = "sessionid", required = false) String sessionid, @RequestParam(value = "lang", required = false) String lang, HttpServletRequest request) {
     ModelAndView mav = new ModelAndView("gamelauncher1");
     mav.addObject("link", link);
     mav.addObject("lang", lang);
     if (usertoken != null) {
       mav.addObject("urllobby", this.http.getServerUrl() + "session/lobby/close?usertoken=" + this.http.getServerUrl() + "&sessiontoken=" + usertoken + "&sessionid=" + sessiontoken);
     } else {
       
       mav.addObject("urllobby", this.http.getServerUrl() + "#/games");
     }  return mav;
   }
 
   
   @PermitAll
   @RequestMapping({"/bartop"})
   public ModelAndView bartop(HttpServletRequest request) {
     ModelAndView mav = new ModelAndView("bartop");
     mav.addObject("sportsbookWalletcode", this.http.getSportsbookWalletcode());
     mav.addObject("sportsbookJs", this.http.getSportsbookJs());
     mav.addObject("sportsbookJsInit", this.http.getSportsbookJsInit());
     return mav;
   }
 
 
   
   @PermitAll
   @RequestMapping({"/liw"})
   public ModelAndView liw(@RequestParam(value = "lang", required = true) String lang, @RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
     ModelAndView mav = new ModelAndView("liw");
     mav.addObject("token", token);
     mav.addObject("currency", this.http.getCurrency());
     mav.addObject("lang", UserUtil.getLangLiw(lang));
     mav.addObject("liwJs", this.http.getLiwJs());
     return mav;
   }
 
 
 
 
   
   @PermitAll
   @RequestMapping({"/signin"})
   public ModelAndView signin(HttpServletRequest request, @RequestParam(value = "affiliateRegisterToken", required = false) String affiliateRegisterToken, @RequestParam(value = "pdsRegisterToken", required = false) String pdsRegisterToken, @RequestParam(value = "lang", required = false) String lang) {
     ModelAndView mav = new ModelAndView("sign-in");
     mav.addObject("currency", this.http.getCurrency());
     mav.addObject("affiliateRegisterToken", affiliateRegisterToken);
     mav.addObject("pdsRegisterToken", pdsRegisterToken);
     mav.addObject("underAge", this.http.getUnderAge());
     mav.addObject("lang", (lang != null) ? lang : this.http.getLocate());
     mav.addObject("googleKey", this.http.getGoogleKey());
     mav.addObject("googleSecret", this.http.getGoogleSecret());
     mav.addObject("googleApiUrl", this.http.getGoogleApiUrl());
     return mav;
   }
 
 
   
   @PermitAll
   @GetMapping({"/thankyou"})
   public ModelAndView thankYou(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId) {
     ModelAndView mav = new ModelAndView("thankyou");
     mav.addObject("userId", userId);
     return mav;
   }
 
 
   
   @PermitAll
   @GetMapping({"/cleancache"})
   public ModelAndView cmsCleanCache(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "timestamp", required = true) String timestamp, @RequestParam(value = "hash", required = true) String hash) {
     try {
       this.cacheCleaner.cleanCache(type, timestamp, hash);
     } catch (Exception e) {
       e.printStackTrace();
     } 
     return new ModelAndView("redirect:/");
   }
   
   @PermitAll
   @GetMapping({"/wrong-location"})
   public ModelAndView wrongLocation() {
     return new ModelAndView("wrong-location");
   }
 }


