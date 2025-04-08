 package com.aldebaran.controller;
 
 import com.aldebaran.auth.APrincipal;
 import com.aldebaran.auth.SessionPrincipalResolver;
 import com.aldebaran.service.CacheCleaner;
 import com.aldebaran.service.HttpService;
 import com.aldebaran.service.MailService;
 import com.aldebaran.service.UserService;
 import com.aldebaran.util.DateUtil;
 import com.aldebaran.util.GeoliteUtil;
 import com.aldebaran.util.MapBuilder;
 import com.aldebaran.util.RegexUtil;
 import com.aldebaran.util.UserUtil;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonMappingException;
 import com.fasterxml.jackson.databind.JsonNode;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import java.security.Principal;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.security.PermitAll;
 import javax.annotation.security.RolesAllowed;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.bind.annotation.RestController;
 
 @RestController
 public class UserController {
private static final Logger logger = LogManager.getLogger(com.aldebaran.controller.UserController.class);
 
   
   @Autowired
   UserService userService;
 
   
   @Autowired
   MailService mailService;
 
   
   @Autowired
   CacheCleaner cacheCleaner;
 
   
   @Autowired
   HttpService http;
   
   @Autowired
   GeoliteUtil geoliteUtil;
 
   
   @PermitAll
   @PostMapping({"/login"})
   public Object login(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password, @RequestParam(value = "externalip", required = false) String externalip, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     APrincipal aPrincipal;
SessionPrincipalResolver.unsetUserPrincipal(request);
MapBuilder<String, String> mb = new MapBuilder();
Principal principal = null;
     
JsonNode result = this.userService.login(username, password);
     
if (result.has("result") && result.get("result").asInt() != 0) {
if (result.get("description").asText().equals("User disabled.")) {
mb.add("messageKo", "User disabled");
       } else {
mb.add("messageKo", "Incorrect username or password");
}  return mb.map();
     } 
     
     try {
String usertoken = result.get("data").get("usertoken").asText();
String sessiontoken = result.get("data").get("sessiontoken").asText();
Integer status = result.get("data").get("status").asInt();
String lastlogin = result.get("data").has("lastLogin") ? result.get("data").get("lastLogin").asText() : 
null;
String firstname = null;
String lastname = null;
String email = null;
String sportsbooktoken = null;
Integer userid = null;
JsonNode user = this.userService.user(usertoken, sessiontoken);
if (user.has("result") && user.get("result").asInt() == 0) {
firstname = user.get("firstname").asText();
lastname = user.get("lastname").asText();
email = user.get("email").asText();
         
         userid = user.get("userid").asInt();
         
         JsonNode otp = this.userService.otp(userid);
         if (otp.has("result") && otp.get("result").asInt() == 0) {
           sportsbooktoken = otp.get("otp").asText();
         } else {
           logger.error("[com.aldebaran] problem with otp user: ",
               otp.has("description") ? otp.get("description").asText() : "");
           mb.add("messageKo", "Problem with otp user");
           return mb.map();
         } 
 
         
         this.userService.setMetadata(userid, username, "ipAddress", (
             externalip != null && !externalip.equals("null")) ? externalip : request.getRemoteAddr());
         this.userService.setMetadata(userid, username, "userAgent", request.getHeader("User-Agent"));
       } else {
         logger.error("[com.aldebaran] problem with user login: ",
             user.has("description") ? user.get("description").asText() : "");
         mb.add("messageKo", "Problem with user login");
         return mb.map();
       } 
 
       
       aPrincipal = new APrincipal(userid, firstname, lastname, email, usertoken, sessiontoken, sportsbooktoken, lastlogin, status, (request.getSession() != null) ? request.getSession().getId() : null);
       
       SessionPrincipalResolver.setUserPrincipal(request, (Principal)aPrincipal);
     } catch (Exception e) {
       logger.error("[com.aldebaran] login error: ", e);
       mb.add("messageKo", "Incorrect username or password");
       return mb.map();
     } 
     
     return aPrincipal;
   }
 
 
 
 
   
   @PermitAll
   @RequestMapping({"/comefrom"})
   public void comefrom(@RequestParam(value = "usertoken", required = false) String usertoken, @RequestParam(value = "sessiontoken", required = false) String sessiontoken, HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
     SessionPrincipalResolver.unsetUserPrincipal(request);
     MapBuilder<String, String> mb = new MapBuilder();
     Principal principal = null;
     
     String firstname = null;
     String lastname = null;
     String email = null;
     String sportsbooktoken = null;
     Integer userid = null;
     Integer status = null;
     String lastlogin = null;
     JsonNode user = this.userService.user(usertoken, sessiontoken);
     if (user.has("result") && user.get("result").asInt() == 0) {
       firstname = user.get("firstname").asText();
       lastname = user.get("lastname").asText();
       email = user.get("email").asText();
       status = user.get("status").asInt();
       lastlogin = user.get("lastLogin").asText();
       
       userid = user.get("userid").asInt();
       
       JsonNode otp = this.userService.otp(userid);
       if (otp.has("result") && otp.get("result").asInt() == 0) {
         sportsbooktoken = otp.get("otp").asText();
       } else {
         logger.error("[com.aldebaran] problem with otp user: ",
             otp.has("description") ? otp.get("description").asText() : "");
         mb.add("messageKo", "Problem with otp user");
         response.sendRedirect("/");
       } 
     } else {
       logger.error("[com.aldebaran] problem with user login: ",
           user.has("description") ? user.get("description").asText() : "");
       mb.add("messageKo", "Problem with user login");
       response.sendRedirect("/");
     } 
     
     APrincipal aPrincipal = new APrincipal(userid, firstname, lastname, email, usertoken, sessiontoken, sportsbooktoken, lastlogin, status, 
         (request.getSession() != null) ? request.getSession().getId() : null);
     
     SessionPrincipalResolver.setUserPrincipal(request, (Principal)aPrincipal);
     response.sendRedirect("/");
   }
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/checkPsd"})
   @ResponseBody
   public Object checkPsd(HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     APrincipal ap = UserUtil.getUser(request);
 
     
     JsonNode metadata = this.userService.getMetadataArray(ap.getId());
     mb.add("isPsdTransferFunds", this.userService.isMetadataProperty(metadata, "psd_transfer_funds"));
     mb.add("isPsdPswError", this.userService.isMetadataProperty(metadata, "psd_psw_error"));
     return mb.map();
   }
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/checkPersonaId"})
   @ResponseBody
   public Object checkPersonaId(HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     APrincipal ap = UserUtil.getUser(request);
 
     
     JsonNode metadata = this.userService.getMetadataArray(ap.getId());
     mb.add("isPsdPersonaId", this.userService.isMetadataProperty(metadata, "persona_id"));
     return mb.map();
   }
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/logout"})
   @ResponseBody
   public Object logout(HttpServletRequest request) {
     MapBuilder<String, Object> mb = new MapBuilder();
     SessionPrincipalResolver.unsetUserPrincipal(request, true);
     mb.add("logout", "ok");
     return mb.map();
   }
 
 
 
   
   @PermitAll
   @PostMapping({"/register"})
   public Object registration(@RequestBody Map<String, Object> requestBody, HttpServletRequest req) throws NoSuchAlgorithmException, IOException {
     MapBuilder<String, Object> mb = new MapBuilder();
     
     JSONObject request = null;
     try {
       request = new JSONObject(requestBody);
     } catch (Exception e) {
       mb.add("error", "malformed json body request");
       return mb.map();
     } 
 
     
     List<String> errors = new ArrayList<>();
     
     String firstname = request.getString("firstname").trim();
     String lastname = request.getString("lastname").trim();
     String city = request.getString("city");
     String country = request.getString("country");
     String birth_date = request.getString("birth_date");
     String email = request.getString("email").trim();
     String taxcode = request.getString("taxcode").trim();
     String password = request.getString("password");
     String sec_questions = request.getString("sec_questions");
     String documentId = request.getString("document_id").trim();
     String documentType = request.getString("document_type");
     String mobile = request.getString("mobile").trim();
     String externalip = null;
     String consensoComercial = null;
     if (request.has("externalip"))
       externalip = request.getString("externalip"); 
     if (request.has("consensoComercial"))
       consensoComercial = request.getString("consensoComercial"); 
     if (firstname == null || "".equals(firstname))
       errors.add("Firstname is required"); 
     if (lastname == null || "".equals(lastname))
       errors.add("Lastname is required"); 
     if (city == null || "".equals(city))
       errors.add("City is required"); 
     if (mobile == null || "".equals(mobile))
       errors.add("Mobile is required"); 
     if (country == null || "".equals(country))
       errors.add("Country is required"); 
     if (birth_date != null || !"".equals(birth_date)) {
       if (!DateUtil.isFormatted(birth_date, "yyyy-MM-dd"))
         errors.add("malformed Birth date"); 
     } else {
       errors.add("Birth date is required");
     } 
     if (email != null || !"".equals(email)) {
       if (!RegexUtil.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", email))
         errors.add("malformed Email"); 
     } else {
       errors.add("Email is required");
     } 
     if (taxcode != null || !"".equals(taxcode)) {
       if (!RegexUtil.matches("^[A-Za-z0-9]*\\d+[A-Za-z0-9]*$", taxcode))
         errors.add("malformed Tax code"); 
     } else {
       errors.add("Tax code is required");
     } 
     if (documentId == null || "".equals(documentId))
       errors.add("Document number id is required"); 
     if (documentType == null || "".equals(documentType))
       errors.add("Document type is required"); 
     if (password == null || "".equals(password))
       errors.add("Password is required"); 
     if (sec_questions == null || "".equals(sec_questions)) {
       errors.add("Security questions is required");
     }
     JSONObject secQuestionsJson = null;
     try {
       secQuestionsJson = new JSONObject(sec_questions);
     } catch (JSONException err) {
       errors.add("Security questions malformed");
     } 
     
     if (!errors.isEmpty()) {
       mb.add("errors", errors);
       return mb.map();
     } 
     request.put("sec_questions", secQuestionsJson);
     request.put("skin", this.http.getSkin());
     
     JsonNode result = this.userService.register(request);
     
     if (result.has("result") && result.get("result").asInt() == 0) {
       int userid = result.get("userid").asInt();
       this.userService.setMetadata(userid, "" + userid, "ipAddressRegistration", (
           externalip != null && !externalip.equals("null")) ? externalip : req.getRemoteAddr());
       this.userService.setMetadata(userid, "" + userid, "psdRegistrationCode", request.getString("registration_code"));
       if (request.has("affiliate_register_token") && request.getString("affiliate_register_token") != null && 
         !request.getString("affiliate_register_token").equals(""))
         this.userService.setMetadata(userid, "" + userid, "affiliateRegisterToken", 
             request.getString("affiliate_register_token")); 
       if (request.has("pds_register_token") && request.getString("pds_register_token") != null && 
         !request.getString("pds_register_token").equals("")) {
         this.userService.setMetadata(userid, "" + userid, "pdsRegisterToken", 
             request.getString("pds_register_token"));
       }
       this.userService.setMetadata(userid, "" + userid, "consensoComercial", (
           consensoComercial != null && !consensoComercial.equals("null")) ? consensoComercial : "false");
     } 
     
     return result;
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/user"})
   public Object user(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.user(usertoken, sessiontoken);
   }
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/user-details"})
   public JsonNode players(@RequestParam(value = "usertoken", required = true) String usertoken) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.list(null, null, usertoken);
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/balances"})
   public Object balances(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.balances(usertoken, sessiontoken);
   }
 
 
 
   
   @PermitAll
   @PostMapping({"/confirm"})
   public Object confirm(@RequestParam(value = "nonce", required = true) String nonce) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.confirmActivate(nonce);
   }
 
 
 
   
   @PermitAll
   @PostMapping({"/reserve"})
   public Object reserve(@RequestParam(value = "email", required = true) String email) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.userService.reservePasswordUpdate(email);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
   
   @PermitAll
   @PostMapping({"/commit"})
   public Object commit(@RequestParam(value = "password", required = true) String password, @RequestParam(value = "otp", required = true) String otp) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.userService.commitPasswordUpdate(password, otp);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/limits/set"})
   public Object limitsSet(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "type", required = true) String type, @RequestParam(value = "value", required = true) Integer value, @RequestParam(value = "description", required = true) String description, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     return this.userService.setLimits(usertoken, sessiontoken, type, value, description, usertoken);
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/limits/list"})
   public Object limits(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     return this.userService.limits(usertoken, sessiontoken);
   }
 
 
 
 
 
 
 
 
   
   @PostMapping({"/sendContactUs"})
   public Object sendContactUs(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "email", required = true) String email, @RequestParam(value = "cedula", required = true) String cedula, @RequestParam(value = "comments", required = true) String comments, @RequestParam(value = "consensoComercial", required = true) String consensoComercial, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     APrincipal ap = UserUtil.getUser(request);
     try {
       this.mailService.sendMailContactUs(name, email, comments, (ap != null) ? ap.getId() : null, consensoComercial, cedula);
       
       mb.add("message", "OK");
     } catch (Exception e) {
       e.printStackTrace();
       mb.add("message", "KO");
     } 
     
     return mb.map();
   }
 
 
   
   @GetMapping({"/documentTypes"})
   public Object documentTypes(@RequestParam(name = "lang") String lang) throws JsonMappingException, JsonProcessingException {
     MapBuilder<String, Object> mbResult = new MapBuilder();
     
     mbResult.add("documentTypes", this.userService.documentTypes(lang));
     return mbResult.map();
   }
 
   
   @GetMapping({"/provinces"})
   public Object provinces() throws JsonMappingException, JsonProcessingException {
     MapBuilder<String, Object> mbResult = new MapBuilder();
     
     mbResult.add("provinces", this.userService.getProvinces());
     return mbResult.map();
   }
 
   
   @GetMapping({"/cities"})
   public Object cities() throws JsonMappingException, JsonProcessingException {
     MapBuilder<String, Object> mbResult = new MapBuilder();
     
     mbResult.add("cities", this.userService.getCities());
     return mbResult.map();
   }
 
 
   
   @PostMapping({"/verifyRecaptcha"})
   public Object verifyRecaptcha(@RequestParam(value = "secret", required = true) String secret, @RequestParam(value = "response", required = true) String response, HttpServletRequest request) throws JsonProcessingException {
     return this.userService.verifyRecaptcha(secret, response);
   }
 
   
   @GetMapping({"/settings/list"})
   public Object settingsList(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.getSettingsList();
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"email/reserve"})
   public Object emailReserve(@RequestParam(value = "email", required = true) String email, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     String userId = UserUtil.getUserId(request);
     JsonNode result = this.userService.reserveEmailUpdate(email, userId);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
 
   
   @PermitAll
   @PostMapping({"email/commit"})
   public Object emailCommit(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "userid", required = true) String userid, @RequestParam(value = "otp", required = true) String otp, @RequestParam(value = "hash", required = true) String hash) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.userService.commitEmailUpdate(email, userid, otp, hash);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"phone/change"})
   public Object phoneChange(@RequestParam(value = "phone", required = true) String phone, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     String userId = UserUtil.getUserId(request);
     JsonNode result = this.userService.changePhone(phone, userId);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"set/selfexcluded"})
   public Object setSelfexcluded(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.userService.setSelfexcluded(usertoken, sessiontoken);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
   
   @PermitAll
   @GetMapping({"/getip"})
   public Object getIp() throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.userService.getIp();
   }
   
   @PermitAll
   @GetMapping({"/checkLocation"})
   public Object checkLocation(@RequestParam(value = "ip", required = true) String ip, HttpServletRequest request) {
     return Map.of("result", this.geoliteUtil.checkIp(ip));
   }
 }


