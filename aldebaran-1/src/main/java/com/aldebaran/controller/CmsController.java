package com.aldebaran.controller;

import com.aldebaran.service.CmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;









@RestController
public class CmsController
{
  @Autowired
  CmsService cmsService;
  
  @Cacheable({"cmsTextCache"})
  @PermitAll
  @PostMapping({"/cms/text/list"})
  public JsonNode textList(@RequestParam(value = "language", required = true) String language, @RequestParam(value = "group", required = false) String group, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "name", required = false) String name) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.cmsService.textList(language, group, category, name);
  }


  
  @Cacheable({"cmsTextCache"})
  @PermitAll
  @PostMapping({"/cms/text/content"})
  public JsonNode textContent(@RequestParam(value = "id", required = true) Integer id) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.cmsService.textContent(id);
  }




  
  @Cacheable({"cmsFileCache"})
  @PermitAll
  @PostMapping({"/cms/file/list"})
  public JsonNode fileList(@RequestParam(value = "language", required = true) String language, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "name", required = false) String name) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.cmsService.fileList(language, category, name);
  }


  
  @Cacheable({"cmsFileCache"})
  @PermitAll
  @PostMapping({"/cms/file/content"})
  public JsonNode fileContent(@RequestParam(value = "id", required = true) Integer id) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
     return this.cmsService.fileContent(id);
  }
}


