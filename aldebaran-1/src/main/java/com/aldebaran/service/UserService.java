 package com.aldebaran.service;
 
 import com.aldebaran.model.Cities;
 import com.aldebaran.model.DocumentTypesResult;
 import com.aldebaran.model.Provinces;
 import com.aldebaran.util.MapBuilder;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonMappingException;
 import com.fasterxml.jackson.databind.JsonNode;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import java.util.List;
 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import javax.persistence.Query;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 
 
 
 
 
 
 
 @Service
 public class UserService
 {
   @Autowired
   com.aldebaran.service.HttpService http;
   @Autowired
   com.aldebaran.service.CryptoService cryptoService;
   @PersistenceContext
   private EntityManager em;
   
   public JsonNode login(String username, String password) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
mb.add("documentid", username);
mb.add("password", password);
String loginHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + username);
mb.add("hash", loginHash);
return mapper.readTree(this.http.newRequestGet("loginbyid", mb.map()));
   }
 
   
   public JsonNode register(JSONObject request) throws JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
return mapper.readTree(this.http.newRequestPost("register", request.toString()));
   }
 
 
   
   public JsonNode user(String usertoken, String sessiontoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + usertoken);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("user", this.http.getUrlReportsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode list(Integer userid, String email, String usertoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String useridP = "";
String emailP = "";
String usertokenP = "";
if (userid != null)
useridP = "" + userid; 
if (email != null)
emailP = email; 
if (usertoken != null) {
usertokenP = usertoken;
     }
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + useridP + emailP);
mb.add("userid", useridP);
mb.add("email", emailP);
mb.add("usertoken", usertokenP);
mb.add("from", "");
mb.add("to", "");
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("user/list/", this.http.getUrlReportsBackend(), mb.map()));
   }
 
 
   
   public JsonNode balances(String usertoken, String sessiontoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + usertoken);
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("balances", this.http.getUrlCoreFrontend(), mb.map()));
   }
 
 
   
   public JsonNode confirmActivate(String nonce) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth());
     mb.add("otp", nonce);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("activate/", mb.map()));
   }
 
   
   public JsonNode reservePasswordUpdate(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth());
     mb.add("email", email);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("password-update/reserve/", mb.map()));
   }
 
   
   public JsonNode commitPasswordUpdate(String password, String otp) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + password);
     mb.add("password", password);
     mb.add("otp", otp);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("password-update-psd/commit/", mb.map()));
   }
 
 
   
   public JsonNode reserveEmailUpdate(String email, String userid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + email);
     mb.add("email", email);
     mb.add("userid", userid);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("email-update/reserve/", mb.map()));
   }
 
 
 
   
   public JsonNode commitEmailUpdate(String email, String userid, String otp, String hash) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     mb.add("email", email);
     mb.add("userid", userid);
     mb.add("otp", otp);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("email-update/commit/", mb.map()));
   }
 
 
   
   public JsonNode changePhone(String phone, String userid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + phone);
     mb.add("phone", phone);
     mb.add("userid", userid);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("phone-update", mb.map()));
   }
 
   
   public JsonNode otp(Integer userid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser());
     mb.add("userid", "" + userid);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("otp/create", this.http.getUrlGamesBackend(), mb.map()));
   }
 
 
 
   
   public JsonNode setLimits(String usertoken, String sessiontoken, String type, Integer value, String description, String operator) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String hash = this.cryptoService.digest(
         this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser() + usertoken + sessiontoken + type + description + value);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("type", type);
     mb.add("description", description);
     mb.add("value", "" + value);
     mb.add("operator", operator);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("user/limits/set", this.http.getUrlCoreFrontend(), mb.map()));
   }
 
 
   
   public JsonNode keepAlive(String usertoken, String sessiontoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + usertoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("keepalive", this.http.getUrlCoreFrontend(), mb.map()));
   }
 
 
   
   public JsonNode limits(String usertoken, String sessiontoken) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser() + usertoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("user/limits/list/", this.http.getUrlReportsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode setMetadata(Integer userid, String operator, String name, String value) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser() + userid + name + value);
     mb.add("userid", "" + userid);
     mb.add("name", name);
     mb.add("value", value);
     mb.add("operator", operator);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("user/metadata/set/", this.http.getUrlCoreBackend(), mb.map()));
   }
 
 
   
   public JsonNode getMetadata(Integer userid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser());
     mb.add("userid", "" + userid);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("user/metadata/list", this.http.getUrlReportsBackend(), mb.map()));
   }
 
 
   
   public JsonNode getMetadataArray(Integer userid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     JsonNode metadataRoot = getMetadata(userid);
     if (metadataRoot.has("result") && metadataRoot.get("result").asInt() == 0) {
       JsonNode metadata = metadataRoot.get("metadata");
       if (metadata.isArray())
         return metadata; 
     } 
     return null;
   }
   
   public boolean isMetadataProperty(JsonNode metadata, String property) {
     if (metadata != null)
       for (JsonNode objNode : (Iterable<JsonNode>)metadata) {
         String name = objNode.get("name").asText();
         if (name.equals(property)) {
           return true;
         }
       }  
     return false;
   }
 
 
   
   public List<DocumentTypesResult> documentTypes(String lang) {
     String sql = "select id, code, description, regular_expression as regularExpression, lang from document_types where lang = :lang ";
 
     
     Query query = this.em.createNativeQuery(sql, "DocumentTypesResult");
     return query.setParameter("lang", lang).getResultList();
   }
 
 
   
   public List<Provinces> getProvinces() {
     String sql = "select id, name from provinces";
     
     Query query = this.em.createNativeQuery(sql, "Provinces");
     return query.getResultList();
   }
 
 
   
   public List<Cities> getCities() {
     String sql = "select id, name, province_id from cities";
     
     Query query = this.em.createNativeQuery(sql, "Cities");
     return query.getResultList();
   }
 
 
   
   public JsonNode getProxypayReferenceId() throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String nonce = this.cryptoService.getSecretSharedNonce();
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
     mb.add("nonce", nonce);
     mb.add("hash", hash);
     return 
       mapper.readTree(this.http.newRequestGet("deposit/proxypay/referenceId", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode verifyRecaptcha(String secret, String response) throws JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     mb.add("secret", secret);
     mb.add("response", response);
     return mapper.readTree(this.http.newRequestPost("", "https://www.google.com/recaptcha/api/siteverify", mb.map()));
   }
 
 
   
   public JsonNode getSettingsList() throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String nonce = this.cryptoService.getSecretSharedNonce();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedOperation() + this.cryptoService.getSecretSharedOperation());
     mb.add("nonce", nonce);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("settings/list/", this.http.getUrlCoreBackend(), mb.map()));
   }
 
 
 
   
   public JsonNode setSelfexcluded(String usertoken, String sessiontoken) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String status = "2";
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedUser() + this.cryptoService.getSecretSharedUser() + usertoken + sessiontoken);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("status", status);
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("players/change/status", this.http.getUrlCoreFrontend(), mb.map()));
   }
 
 
   
   public JsonNode getIp() throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     return mapper.readTree(this.http.newRequestGet("", "https://jsonip.com", mb.map()));
   }
 }


