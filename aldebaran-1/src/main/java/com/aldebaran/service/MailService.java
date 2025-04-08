 package com.aldebaran.service;
 
 import freemarker.template.TemplateException;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.io.StringWriter;
 import java.security.NoSuchAlgorithmException;
 import java.util.AbstractMap;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Map;
 import java.util.Set;
 import javax.mail.MessagingException;
 import javax.mail.internet.MimeMessage;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.collections4.MapUtils;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.mail.javamail.MimeMessageHelper;
 import org.springframework.stereotype.Service;
 import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
 import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

 @Service
 public class MailService
 {
private static final Logger logger = LogManager.getLogger(com.aldebaran.service.MailService.class);
 
 
   
   @Value("${mail.from}")
   private String mailFrom;
 
 
   
   @Value("${mail.exceptionTo}")
   private String mailExceptionTo;
 
 
   
   @Value("${mail.exceptionSubject}")
   private String mailExceptionSubject;
 
   
   @Value("${mail.contactUsTo}")
   private String mailContactUsTo;
 
   
   @Value("${mail.contactUsSubject}")
   private String mailContactUsSubject;
 
   
   @Value("${server.url}")
   private String url;
 
   
   @Autowired
   private JavaMailSender emailSender;
 
   
   @Autowired
   private FreeMarkerConfigurer freemarkerConfig;
 
   
   @Autowired
   private com.aldebaran.service.UserService userService;
 
 
   
   public void sendUnhandledException(Throwable e, HttpServletRequest request, HttpServletResponse response, Map<String, Object> vars) throws MessagingException, IOException, TemplateException {
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
e.printStackTrace(pw);
String sStackTrace = sw.toString();
     
Map<String, Object> allVars = new HashMap<>();
if (vars != null) {
allVars.putAll(vars);
     }
Set<Map.Entry<String, String[]>> parameters = new HashSet<>();
ArrayList<String> headerNames = new ArrayList<>();
     
if (request != null) {
 
 
       
       for (Map.Entry<String, String[]> parameter : (Iterable<Map.Entry<String, String[]>>)request.getParameterMap().entrySet()) {
         if (((String)parameter.getKey()).toLowerCase().contains("password")) {
           parameters.add((Map.Entry)new AbstractMap.SimpleEntry<>(parameter.getKey(), new String[] { "*****" }));
           continue;
         } 
         parameters.add((Map.Entry)new AbstractMap.SimpleEntry<>((Map.Entry)parameter));
       } 
 
       
       if (request != null) {
         Enumeration<String> en = request.getHeaderNames();
         while (en.hasMoreElements()) {
           String headerName = ((String)en.nextElement()).toString();
           if (headerName.equalsIgnoreCase("cookie")) {
             continue;
           }
           headerNames.add(headerName);
         } 
       } 
     } 
     
     allVars.put("exception", e);
     allVars.put("stackTrace", sStackTrace);
     allVars.put("request", request);
     allVars.put("headerNames", headerNames);
     allVars.put("parameters", parameters);
     allVars.put("response", response);
     
     if (this.mailExceptionTo != null && !this.mailExceptionTo.isEmpty()) {
       send("unhandledException.ftl", this.mailExceptionSubject, this.mailExceptionTo, null, allVars);
     } else {
       logger.debug("[com.aldebaran] ### Eccezione non gestita ma nessuna email di destinazione impostata ###");
     } 
   }
 
 
 
   
   public void sendMailContactUs(String name, String email, String comments, Integer userid, String consensoComercial, String cedula) throws MessagingException, IOException, TemplateException, NoSuchAlgorithmException {
     Map<String, Object> allVars = new HashMap<>();
     allVars.put("name", name);
     allVars.put("comments", comments);
     allVars.put("cedula", cedula);
     
     if (this.mailContactUsTo != null && !this.mailContactUsTo.isEmpty()) {
       send("contactUs.ftl", this.mailContactUsSubject + " " + this.mailContactUsSubject, this.mailContactUsTo, email, allVars);
     } else {
       logger.debug("[com.aldebaran] ### Eccezione non gestita ma nessuna email di destinazione impostata ###");
     } 
     
     if (userid != null)
     {
       this.userService.setMetadata(userid, "" + userid, "consensoComercial", (
           consensoComercial != null && !consensoComercial.equals("null")) ? consensoComercial : "false");
     }
   }
 
 
 
 
   
   public boolean sendEmail(String email, String templateName, String subject, String from, Map<String, Object> map) throws Exception {
     try {
       send(templateName, subject, email, from, map);
       return true;
     } catch (Exception e) {
       logger.error("[com.aldebaran] errore invio email: ", e);
       throw new Exception();
     } 
   }
 
 
 
   
   public boolean sendEmail(String[] emails, String templateName, String subject, String from, Map<String, Object> map) throws Exception {
     try {
       send(templateName, subject, emails, from, map);
       return true;
     } catch (Exception e) {
       logger.error("[com.aldebaran] errore invio email: ", e);
       throw new Exception();
     } 
   }
 
 
   
   public void send(String templateName, String subject, String dst, String from, Map<String, Object> vars) throws MessagingException, IOException, TemplateException {
     Map<String, Object> allVars = new HashMap<>();
     allVars.put("timestamp", new Date());
     allVars.put("url", this.url);
     if (MapUtils.isNotEmpty(vars)) {
       allVars.putAll(vars);
     }
     
     String content = FreeMarkerTemplateUtils.processTemplateIntoString(this.freemarkerConfig.getConfiguration().getTemplate(templateName), allVars);
     
     MimeMessage message = this.emailSender.createMimeMessage();
     message.setSubject(subject);
     
     MimeMessageHelper helper = new MimeMessageHelper(message, true);
     if (from != null) {
       helper.setFrom(from);
     } else {
       helper.setFrom(this.mailFrom);
     }  helper.setTo(dst);
     helper.setText(content, true);
     logger.info("[com.aldebaran] Send email [" + subject + "] to " + dst);
     this.emailSender.send(message);
   }
 
 
   
   public void send(String templateName, String subject, String[] dst, String from, Map<String, Object> vars) throws MessagingException, IOException, TemplateException {
     Map<String, Object> allVars = new HashMap<>();
     allVars.put("timestamp", new Date());
     allVars.put("url", this.url);
     if (MapUtils.isNotEmpty(vars)) {
       allVars.putAll(vars);
     }
     
     String content = FreeMarkerTemplateUtils.processTemplateIntoString(this.freemarkerConfig.getConfiguration().getTemplate(templateName), allVars);
     
     MimeMessage message = this.emailSender.createMimeMessage();
     message.setSubject(subject);
     
     MimeMessageHelper helper = new MimeMessageHelper(message, true);
     if (from != null) {
       helper.setFrom(from);
     } else {
       helper.setFrom(this.mailFrom);
     }  helper.setTo(dst);
     helper.setText(content, true);
     logger.info("[com.aldebaran] Send email [" + subject + "] ");
     this.emailSender.send(message);
   }
   
   public String getUrl() {
     return this.url;
   }
 }


