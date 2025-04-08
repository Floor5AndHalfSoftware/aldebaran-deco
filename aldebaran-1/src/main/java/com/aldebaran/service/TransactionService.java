 package com.aldebaran.service;
 
 import com.aldebaran.util.MapBuilder;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.JsonMappingException;
 import com.fasterxml.jackson.databind.JsonNode;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import java.io.UnsupportedEncodingException;
 import java.security.NoSuchAlgorithmException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 
 
 
 
 
 
 @Service
 public class TransactionService
 {
   @Autowired
   com.aldebaran.service.HttpService http;
   @Autowired
   com.aldebaran.service.CryptoService cryptoService;
   
   public JsonNode listByToken(String usertoken, String sessiontoken, String from, String to, String type, String optype, String state) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
String fromP = "";
if (from != null)
fromP = from; 
String toP = "";
if (to != null)
toP = to; 
String typeP = "";
if (type != null)
typeP = type; 
String optypeP = "";
if (optype != null)
optypeP = optype; 
String stateP = "";
if (state != null) {
stateP = state;
     }
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + fromP + toP + typeP + optypeP);
     
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("from", fromP);
mb.add("to", toP);
mb.add("type", typeP);
mb.add("optype", optypeP);
mb.add("state", stateP);
     
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("transaction/listbytokens/", this.http.getUrlReportsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode metadata(String transactionid) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
mb.add("transactionid", transactionid);
mb.add("hash", hash);
return mapper.readTree(this.http.newRequestGet("transaction/metadata/", this.http.getUrlReportsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode deposit(String usertoken, String sessiontoken, String amount, String currency) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
     
String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount);
     
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
mb.add("amount", amount);
mb.add("currency", currency);
mb.add("hash", userHash);
return mapper.readTree(this.http.newRequestGet("deposit/proxypay/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode wiretransfer(String usertoken, String sessiontoken, String amount, String currency, String accountholder, String accountiban) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
ObjectMapper mapper = new ObjectMapper();
MapBuilder<String, String> mb = new MapBuilder();
String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount + currency + accountholder);
 
     
mb.add("usertoken", usertoken);
mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     mb.add("accountholder", accountholder);
     mb.add("accountiban", accountiban);
     
     mb.add("hash", userHash);
     return 
       mapper.readTree(this.http.newRequestGet("withdraw/wiretransfer/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode proxypayList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
 
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("deposit/proxypay/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode wiretransferList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
 
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     mb.add("hash", hash);
     return 
       mapper.readTree(this.http.newRequestGet("withdraw/wiretransfer/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
 
   
   public JsonNode monnet(String usertoken, String sessiontoken, String amount, String currency, String bankcode, String accounttype, String accountnumber) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount + currency + bankcode + accounttype);
 
 
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     mb.add("bankcode", bankcode);
     mb.add("accounttype", accounttype);
     mb.add("accountnumber", accountnumber);
     
     mb.add("hash", userHash);
     return 
       mapper.readTree(this.http.newRequestGet("withdraw/monnet/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode monnetWithdrawalsList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
 
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     mb.add("hash", hash);
     return 
       mapper.readTree(this.http.newRequestGet("withdraw/monnet/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode monnetBankList() throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
 
     
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
     mb.add("nonce", this.cryptoService.getSecretSharedNonce());
     mb.add("hash", hash);
     return 
       mapper.readTree(this.http.newRequestGet("withdraw/monnet/bank/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPlacetopayReserve(String usertoken, String sessiontoken, String amount, String currency) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     
     mb.add("hash", userHash);
     return 
       mapper.readTree(this.http.newRequestGet("deposit/placetopay/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPlacetopayCheck(String usertoken, String sessiontoken, String reference, String requestid) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String requestidP = "";
     if (requestid != null) {
       requestidP = requestid;
     }
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + reference);
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("reference", reference);
     mb.add("requestid", requestidP);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/placetopay/check", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPlacetopayList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/placetopay/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
   
   public JsonNode transactionFees() throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     String nonce = this.cryptoService.getSecretSharedNonce();
     
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
     
     mb.add("nonce", nonce);
     mb.add("hash", userHash);
     
     return mapper.readTree(this.http.newRequestGet("transaction/fees/", this.http.getUrlReportsBackend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositNuveiReserve(String usertoken, String sessiontoken, String amount, String currency, String origin, String paymentMethod) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount + currency + origin);
 
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     mb.add("origin", origin);
     mb.add("paymentMethod", paymentMethod);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/nuvei/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
 
 
 
 
 
   
   public JsonNode depositNuveiResponse(String ppp_status, String cardCompany, String nameOnCard, String currency, String PPP_TransactionID, String productId, String customData, String payment_method, String responseTimeStamp, String message, String Error, String userPaymentOptionId, String Status, String ClientUniqueID, String ExErrCode, String ErrCode, String AuthCode, String ReasonCode, String Token, String tokenId, String totalAmount, String TransactionID, String dynamicDescriptor, String uniqueCC, String orderTransactionId, String item_amount_1, String item_quantity_1, String advanceResponseChecksum) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction());
     
     mb.add("ppp_status", ppp_status);
     mb.add("cardCompany", cardCompany);
     mb.add("nameOnCard", nameOnCard);
     mb.add("currency", currency);
     mb.add("PPP_TransactionID", PPP_TransactionID);
     mb.add("productId", productId);
     mb.add("customData", customData);
     mb.add("payment_method", payment_method);
     mb.add("responseTimeStamp", responseTimeStamp);
     mb.add("message", message);
     mb.add("Error", Error);
     mb.add("userPaymentOptionId", userPaymentOptionId);
     mb.add("Status", Status);
     mb.add("ClientUniqueID", ClientUniqueID);
     mb.add("ExErrCode", ExErrCode);
     mb.add("ErrCode", ErrCode);
     mb.add("AuthCode", AuthCode);
     mb.add("ReasonCode", ReasonCode);
     mb.add("Token", Token);
     mb.add("tokenId", tokenId);
     mb.add("totalAmount", totalAmount);
     mb.add("TransactionID", TransactionID);
     mb.add("dynamicDescriptor", dynamicDescriptor);
     mb.add("uniqueCC", uniqueCC);
     mb.add("orderTransactionId", orderTransactionId);
     mb.add("item_amount_1", item_amount_1);
     mb.add("item_quantity_1", item_quantity_1);
     mb.add("advanceResponseChecksum", advanceResponseChecksum);
     mb.add("hash", userHash);
     
     return mapper.readTree(this.http.newRequestGet("deposit/nuvei/response", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
 
   
   public JsonNode depositNuveiList(String usertoken, String sessiontoken, String from, String to, String paymentMethod) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     String paymentMethodP = "";
     if (paymentMethod != null) {
       paymentMethodP = paymentMethod;
     }
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from + to);
 
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     mb.add("payment_method", paymentMethodP);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/nuvei/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPaycashReserve(String usertoken, String sessiontoken, String amount, String currency) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/paycash/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPaycashList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/paycash/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
 
   
   public JsonNode depositMonnetReserve(String usertoken, String sessiontoken, String amount, String currency, String discountCoupon, String payinMethod) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount + currency + discountCoupon);
 
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     mb.add("discountcoupon", discountCoupon);
     mb.add("payinmethod", payinMethod);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/monnet/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositMonnetList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/monnet/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositNicoReserve(String usertoken, String sessiontoken, String amount, String currency) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + amount);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     mb.add("currency", currency);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/nico/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositNicoList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/nico/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPayphoneList(String usertoken, String sessiontoken, String from, String to) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken + from);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("from", from);
     mb.add("to", to);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/payphone/list", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPayphoneReserve(String usertoken, String sessiontoken, String amount) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + usertoken + sessiontoken);
     
     mb.add("usertoken", usertoken);
     mb.add("sessiontoken", sessiontoken);
     mb.add("amount", amount);
     
     mb.add("hash", userHash);
     return mapper.readTree(this.http.newRequestGet("deposit/payphone/reserve", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 
 
 
   
   public JsonNode depositPayphoneResponse(String id, String clientTransactionId) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
     
     String userHash = this.cryptoService.digest(this.cryptoService.getSecretSharedTransaction() + this.cryptoService.getSecretSharedTransaction() + id);
     
     mb.add("id", id);
     mb.add("clientTransactionId", clientTransactionId);
     
     mb.add("hash", userHash);
     return 
       mapper.readTree(this.http.newRequestGet("deposit/payphone/response", this.http.getUrlPaymentsFrontend(), mb.map()));
   }
 }


