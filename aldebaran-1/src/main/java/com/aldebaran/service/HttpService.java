 package com.aldebaran.service;
 
 import java.util.Map;
 import okhttp3.FormBody;
 import okhttp3.HttpUrl;
 import okhttp3.MediaType;
 import okhttp3.OkHttpClient;
 import okhttp3.Request;
 import okhttp3.RequestBody;
 import okhttp3.Response;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Service;
 
 
 
 @Service
 public class HttpService
 {
private static final Logger logger = LogManager.getLogger(com.aldebaran.service.HttpService.class);
   
   public static final String CONTENT_TYPE_JSON = "application/json";
   
   public static final String CONTENT_TYPE_X_WWW_FORM = "application/x-www-form-urlencoded";
   
   public static final String CONTENT_TYPE_IMG = "image/jpeg";
   
   @Value("${server.url}")
   private String serverUrl;
   
   @Value("${ws.url.core.frontend}")
   private String urlCoreFrontend;
   
   @Value("${ws.url.core.backend}")
   private String urlCoreBackend;
   
   @Value("${ws.url.payments.frontend}")
   private String urlPaymentsFrontend;
   
   @Value("${ws.url.games.frontend}")
   private String urlGamesFrontend;
   
   @Value("${ws.url.games.backend}")
   private String urlGamesBackend;
   
   @Value("${ws.url.reports.backend}")
   private String urlReportsBackend;
   
   @Value("${ws.url.reports.frontend}")
   private String urlReportsFrontend;
   
   @Value("${ws.url.cms}")
   private String urlCms;
   
   @Value("${client.currency}")
   private String currency;
   
   @Value("${client.locate}")
   private String locate;
   
   @Value("${client.operator}")
   private String operator;
   
   @Value("${client.gsNewLauncher}")
   private String gsNewLauncher;
   
   @Value("${client.gsNewLauncher1}")
   private String gsNewLauncher1;
   
   @Value("${client.sportsbook.walletcode}")
   private String sportsbookWalletcode;
   
   @Value("${client.sportsbook.js}")
   private String sportsbookJs;
   
   @Value("${client.sportsbook.jsinit}")
   private String sportsbookJsInit;
   
   @Value("${client.liw.js}")
   private String liwJs;
   
   @Value("${client.js.debug}")
   private String jsDebug;
   
   @Value("${client.lastdays}")
   private Integer lastDays;
   
   @Value("${underAge}")
   private Integer underAge;
   
   @Value("${betMethod}")
   private String betMethod;
   
   @Value("${client.urlsso}")
   private String urlSso;
   
   @Value("${client.skin}")
   private String skin;
   
   @Value("${client.clearsale.clientid}")
   private String clearsaleClientId;
   
   @Value("${loteria.url.detail}")
   private String loteriaUrlDetail;
   
   @Value("${minigames}")
   private String[] minigames;
   
   @Value("${google.recaptcha.key}")
   private String googleKey;
   
   @Value("${google.recaptcha.secret}")
   private String googleSecret;
   
   @Value("${googleApiUrl}")
   private String googleApiUrl;
   
   @Value("${smartico.publicBrandKey}")
   private String smarticoPublicBrandKey;
   @Value("${smartico.publicLabelKey}")
   private String smarticoPublicLabelKey;
   @Autowired
   OkHttpClient client;
   
   public String newRequestGet(String path, Map<String, String> params) {
     return newRequestGet(path, null, params);
   }
   
   public String newRequestPost(String path, String json) {
     return newRequestPost(path, null, json);
   }
 
   
   public String newRequestGet(String path, String externalUrl, Map<String, String> params) {
     String urlFinal = this.urlCoreFrontend;
     if (externalUrl != null) {
       urlFinal = externalUrl;
     }
     HttpUrl.Builder httpBuilder = HttpUrl.parse(urlFinal + urlFinal).newBuilder();
     if (params != null) {
       for (Map.Entry<String, String> param : params.entrySet()) {
         httpBuilder.addQueryParameter(param.getKey(), param.getValue());
       }
     }
     Request request = new Request.Builder()
       .url(httpBuilder.build())
       .get()
       .addHeader("Content-Type", "application/json")
       .addHeader("Accept", "*/*")
       .addHeader("Cache-Control", "no-cache")
       .build();
     
     return newCall(this.client, request, urlFinal + urlFinal, "GET");
   }
 
   
   public String newRequestPost(String path, String externalUrl, Map<String, String> params) {
     String urlFinal = this.urlCoreFrontend;
     if (externalUrl != null) {
       urlFinal = externalUrl;
     }
     FormBody.Builder formBodyBuilder = new FormBody.Builder();
     if (params != null) {
       for (Map.Entry<String, String> param : params.entrySet()) {
         formBodyBuilder.add(param.getKey(), param.getValue());
       }
     }
     FormBody formBody = formBodyBuilder.build();
     Request request = new Request.Builder()
       .url(urlFinal + urlFinal)
       .post((RequestBody)formBody)
       .addHeader("Content-Type", "application/json")
       .addHeader("Accept", "*/*")
       .addHeader("Cache-Control", "no-cache")
       .build();
     
     return newCall(this.client, request, urlFinal + urlFinal, "POST");
   }
 
 
   
   public String newRequestPost(String path, String externalUrl, String json) {
     String urlFinal = this.urlCoreFrontend;
     if (externalUrl != null) {
       urlFinal = externalUrl;
     }
     MediaType mediaType = MediaType.parse("application/json");
     RequestBody body = RequestBody.create(mediaType, (json != null) ? json : new JSONObject().toString());
     Request request = new Request.Builder()
       .url(urlFinal + urlFinal)
       .post(body)
       .addHeader("Content-Type", "application/json")
       .addHeader("Accept", "*/*")
       .addHeader("Cache-Control", "no-cache")
       .build();
     
     return newCall(this.client, request, urlFinal + urlFinal, "POST");
   }
   private String newCall(OkHttpClient client, Request request, String url, String method) {
     
     try { Response response = client.newCall(request).execute(); 
       try { String body = response.body().string();
         logger.info("[com.aldebaran] " + method + " " + url + " - response " + response.code());
         String str1 = body;
         if (response != null) response.close();  return str1; } catch (Throwable throwable) { if (response != null) try { response.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception e)
     { logger.error("[com.aldebaran] " + method + " " + url + " - response error: ", e);
       return null; }
   
   }
   
   public String getUrlCoreFrontend() {
     return this.urlCoreFrontend;
   }
   
   public String getUrlPaymentsFrontend() {
     return this.urlPaymentsFrontend;
   }
   
   public String getUrlGamesFrontend() {
     return this.urlGamesFrontend;
   }
   
   public String getUrlGamesBackend() {
     return this.urlGamesBackend;
   }
   
   public String getServerUrl() {
     return this.serverUrl;
   }
   
   public String getJsDebug() {
     return this.jsDebug;
   }
   
   public Integer getLastDays() {
     return this.lastDays;
   }
   
   public Integer getUnderAge() {
     return this.underAge;
   }
   
   public String getBetMethod() {
     return this.betMethod;
   }
   
   public String getUrlCms() {
     return this.urlCms;
   }
   
   public String getCurrency() {
     return this.currency;
   }
   
   public String getLocate() {
     return this.locate;
   }
   
   public String getSportsbookWalletcode() {
     return this.sportsbookWalletcode;
   }
   
   public String getOperator() {
     return this.operator;
   }
   
   public String getGsNewLauncher() {
     return this.gsNewLauncher;
   }
   
   public String getGsNewLauncher1() {
     return this.gsNewLauncher1;
   }
   
   public String getUrlCoreBackend() {
     return this.urlCoreBackend;
   }
   
   public String getUrlReportsBackend() {
     return this.urlReportsBackend;
   }
   
   public String getUrlReportsFrontend() {
     return this.urlReportsFrontend;
   }
   
   public String getSportsbookJs() {
     return this.sportsbookJs;
   }
   
   public String getSportsbookJsInit() {
     return this.sportsbookJsInit;
   }
   
   public String getUrlSso() {
     return this.urlSso;
   }
   
   public String getSkin() {
     return this.skin;
   }
   
   public String getLoteriaUrlDetail() {
     return this.loteriaUrlDetail;
   }
   
   public String[] getMinigames() {
     return this.minigames;
   }
   
   public String getLiwJs() {
     return this.liwJs;
   }
   
   public String getClearsaleClientId() {
     return this.clearsaleClientId;
   }
   
   public String getGoogleKey() {
     return this.googleKey;
   }
   
   public String getGoogleSecret() {
     return this.googleSecret;
   }
   
   public String getGoogleApiUrl() {
     return this.googleApiUrl;
   }
   
   public String getSmarticoPublicBrandKey() {
     return this.smarticoPublicBrandKey;
   }
   
   public String getSmarticoPublicLabelKey() {
     return this.smarticoPublicLabelKey;
   }
 }


