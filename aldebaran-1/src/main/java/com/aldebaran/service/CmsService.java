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
public class CmsService
{
  @Autowired
  com.aldebaran.service.HttpService http;
  @Autowired
  com.aldebaran.service.CryptoService cryptoService;
  
  public JsonNode textList(String language, String group, String category, String name) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String nameP = "";
     String categoryP = "";
     String groupP = "";
     String statusP = "1";
     if (name != null) nameP = name; 
     if (category != null) categoryP = category; 
     if (group != null) groupP = group;
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + language + nameP + categoryP + groupP);
     mb.add("language", language);
     mb.add("name", nameP);
     mb.add("category", categoryP);
     mb.add("group", groupP);
     mb.add("status", statusP);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("text/list", this.http.getUrlCms(), mb.map()));
  }

  
  public JsonNode textContent(Integer id) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth());
     mb.add("id", "" + id);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("text/content", this.http.getUrlCms(), mb.map()));
  }



  
  public JsonNode fileList(String language, String category, String name) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String categoryP = "";
     String nameP = "";
     String statusP = "1";
     if (category != null) categoryP = category; 
     if (name != null) nameP = name;
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth() + categoryP + language + nameP);
     mb.add("category", categoryP);
     mb.add("language", language);
     mb.add("name", nameP);
     mb.add("status", statusP);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("file/list", this.http.getUrlCms(), mb.map()));
  }

  
  public JsonNode fileContent(Integer id) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
     ObjectMapper mapper = new ObjectMapper();
     MapBuilder<String, String> mb = new MapBuilder();
    
     String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedAuth() + this.cryptoService.getSecretSharedAuth());
     mb.add("id", "" + id);
     mb.add("hash", hash);
     return mapper.readTree(this.http.newRequestGet("file/content", this.http.getUrlCms(), mb.map()));
  }
}


