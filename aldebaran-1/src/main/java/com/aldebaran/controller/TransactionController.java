 package com.aldebaran.controller;
 
 import com.aldebaran.service.TransactionService;
 import com.aldebaran.util.MapBuilder;
 import com.aldebaran.util.UserUtil;
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
 public class TransactionController
 {
   @Autowired
   TransactionService transactionService;
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/transaction/listbytokens"})
   public Object listbytokens(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "optype", required = false) String optype, @RequestParam(value = "state", required = false) String state) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
return this.transactionService.listByToken(usertoken, sessiontoken, from, to, type, optype, state);
   }
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/transaction/metadata"})
   public Object metadata(@RequestParam(value = "transactionid", required = true) String transactionid) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
return this.transactionService.metadata(transactionid);
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit"})
   public Object deposit(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
if (UserUtil.isUserSelfexcluded(request)) {
return UserUtil.messageUserSelfexcluded();
     }
MapBuilder<String, Object> mb = new MapBuilder();
JsonNode result = this.transactionService.deposit(usertoken, sessiontoken, amount, currency);
if (result.has("result") && result.get("result").asInt() != 0) {
mb.add("messageKo", result.get("description").asText());
mb.add("result", result.get("result").asInt());
return mb.map();
     } 
return result;
   }
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/wiretransfer"})
   public Object wiretransfer(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "accountholder", required = true) String accountholder, @RequestParam(value = "accountiban", required = true) String accountiban, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
MapBuilder<String, Object> mb = new MapBuilder();
JsonNode result = this.transactionService.wiretransfer(usertoken, sessiontoken, amount, currency, accountholder, accountiban);
     
if (result.has("result") && result.get("result").asInt() != 0) {
mb.add("messageKo", result.get("description").asText());
mb.add("result", result.get("result").asInt());
return mb.map();
     } 
return result;
   }
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/monnet"})
   public Object monnet(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "bankcode", required = true) String bankcode, @RequestParam(value = "accounttype", required = true) String accounttype, @RequestParam(value = "accountnumber", required = true) String accountnumber, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.monnet(usertoken, sessiontoken, amount, currency, bankcode, accounttype, accountnumber);
     
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/monnet/bank/list"})
   public Object monnetBankList(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.monnetBankList();
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/proxypay/list"})
   public Object proxypayList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.proxypayList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/wiretransfer/list"})
   public Object withdrawalsList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.wiretransferList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/monnet/list"})
   public Object monnetWithdrawalsList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.monnetWithdrawalsList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/placetopay/reserve"})
   public Object depositPlacetopayReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositPlacetopayReserve(usertoken, sessiontoken, amount, currency);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
   
   @PostMapping({"/deposit/placetopay/check"})
   public Object depositPlacetopayCheck(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "reference", required = true) String reference, @RequestParam(value = "requestid", required = false) String requestid) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositPlacetopayCheck(usertoken, sessiontoken, reference, requestid);
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/placetopay/list"})
   public Object depositPlacetopayList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositPlacetopayList(usertoken, sessiontoken, from, to);
   }
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/transaction/fees"})
   public Object transactionFees() throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.transactionFees();
   }
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/nuvei/reserve"})
   public Object depositNuveiReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "origin", required = true) String origin, @RequestParam(value = "paymentMethod", required = true) String paymentMethod, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositNuveiReserve(usertoken, sessiontoken, amount, currency, origin, paymentMethod);
     
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/nuvei/response"})
   public Object depositNuveiSuccess(@RequestParam(value = "ppp_status", required = true) String ppp_status, @RequestParam(value = "cardCompany", required = false) String cardCompany, @RequestParam(value = "nameOnCard", required = false) String nameOnCard, @RequestParam(value = "currency", required = false) String currency, @RequestParam(value = "PPP_TransactionID", required = false) String PPP_TransactionID, @RequestParam(value = "productId", required = false) String productId, @RequestParam(value = "customData", required = false) String customData, @RequestParam(value = "payment_method", required = false) String payment_method, @RequestParam(value = "responseTimeStamp", required = false) String responseTimeStamp, @RequestParam(value = "message", required = false) String message, @RequestParam(value = "Error", required = false) String Error, @RequestParam(value = "userPaymentOptionId", required = false) String userPaymentOptionId, @RequestParam(value = "Status", required = false) String Status, @RequestParam(value = "ClientUniqueID", required = false) String ClientUniqueID, @RequestParam(value = "ExErrCode", required = false) String ExErrCode, @RequestParam(value = "ErrCode", required = false) String ErrCode, @RequestParam(value = "AuthCode", required = false) String AuthCode, @RequestParam(value = "ReasonCode", required = false) String ReasonCode, @RequestParam(value = "Token", required = false) String Token, @RequestParam(value = "tokenId", required = false) String tokenId, @RequestParam(value = "totalAmount", required = false) String totalAmount, @RequestParam(value = "TransactionID", required = false) String TransactionID, @RequestParam(value = "dynamicDescriptor", required = false) String dynamicDescriptor, @RequestParam(value = "uniqueCC", required = false) String uniqueCC, @RequestParam(value = "orderTransactionId", required = false) String orderTransactionId, @RequestParam(value = "item_amount_1", required = false) String item_amount_1, @RequestParam(value = "item_quantity_1", required = false) String item_quantity_1, @RequestParam(value = "advanceResponseChecksum", required = false) String advanceResponseChecksum) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositNuveiResponse(ppp_status, cardCompany, nameOnCard, currency, PPP_TransactionID, productId, customData, payment_method, responseTimeStamp, message, Error, userPaymentOptionId, Status, ClientUniqueID, ExErrCode, ErrCode, AuthCode, ReasonCode, Token, tokenId, totalAmount, TransactionID, dynamicDescriptor, uniqueCC, orderTransactionId, item_amount_1, item_quantity_1, advanceResponseChecksum);
   }
 
 
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/nuvei/list"})
   public Object depositNuveiList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to, @RequestParam(value = "paymentMethod", required = false) String paymentMethod) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositNuveiList(usertoken, sessiontoken, from, to, paymentMethod);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/paycash/reserve"})
   public Object depositPaycashReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositPaycashReserve(usertoken, sessiontoken, amount, currency);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/paycash/list"})
   public Object depositPaycashList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositPaycashList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/monnet/reserve"})
   public Object depositMonnetReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, @RequestParam(value = "discountcoupon", required = true) String discountCoupon, @RequestParam(value = "payinmethod", required = true) String payinMethod, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositMonnetReserve(usertoken, sessiontoken, amount, currency, discountCoupon, payinMethod);
     
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/monnet/list"})
   public Object depositMonnetList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositMonnetList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/nico/reserve"})
   public Object depositNicoReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "currency", required = true) String currency, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositNicoReserve(usertoken, sessiontoken, amount, currency);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/nico/list"})
   public Object depositNicoList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositNicoList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/payphone/list"})
   public Object depositPayphoneList(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "from", required = true) String from, @RequestParam(value = "to", required = true) String to) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.transactionService.depositPayphoneList(usertoken, sessiontoken, from, to);
   }
 
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/payphone/reserve"})
   public Object depositPayphoneReserve(@RequestParam(value = "usertoken", required = true) String usertoken, @RequestParam(value = "sessiontoken", required = true) String sessiontoken, @RequestParam(value = "amount", required = true) String amount, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositPayphoneReserve(usertoken, sessiontoken, amount);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 
 
 
 
   
   @RolesAllowed({"authenticated"})
   @PostMapping({"/deposit/payphone/response"})
   public Object depositPayphoneResponse(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "clientTransactionId", required = true) String clientTransactionId, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     if (UserUtil.isUserSelfexcluded(request)) {
       return UserUtil.messageUserSelfexcluded();
     }
     MapBuilder<String, Object> mb = new MapBuilder();
     JsonNode result = this.transactionService.depositPayphoneResponse(id, clientTransactionId);
     if (result.has("result") && result.get("result").asInt() != 0) {
       mb.add("messageKo", result.get("description").asText());
       mb.add("result", result.get("result").asInt());
       return mb.map();
     } 
     return result;
   }
 }


