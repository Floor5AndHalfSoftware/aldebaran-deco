 package com.aldebaran.model;
 
 import com.aldebaran.util.DateUtil;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 import com.fasterxml.jackson.annotation.JsonProperty;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import java.io.IOException;
 import java.util.Date;
 
 
 @JsonIgnoreProperties(ignoreUnknown = true)
 public class Game
 {
public static final Integer LAST_DAYS = 10;
   
   private String category;
   
   private Integer gameID;
   private com.aldebaran.model.GameParams gameParams;
   private String name;
   private String playLinkDemo;
   private String thumbnailPath;
   private Date ts;
   private Integer value;
   
   public String getCategory() {
return this.category;
   }
 
   
   public void setCategory(String category) {
this.category = category;
   }
 
   
   public Integer getGameID() {
return this.gameID;
   }
 
   
   public void setGameID(Integer gameID) {
this.gameID = gameID;
   }
 
   
   public com.aldebaran.model.GameParams getGameParams() {
return this.gameParams;
   }
 
 
   
   @JsonProperty("gameParams")
   public void setGameParams(String gameParams) throws IOException {
ObjectMapper mapper = new ObjectMapper();
this.gameParams = (com.aldebaran.model.GameParams)mapper.readValue(gameParams, com.aldebaran.model.GameParams.class);
   }
 
   
   public String getName() {
return this.name;
   }
 
   
   public void setName(String name) {
this.name = name;
   }
 
   
   public String getPlayLinkDemo() {
return this.playLinkDemo;
   }
 
   
   public void setPlayLinkDemo(String playLinkDemo) {
this.playLinkDemo = playLinkDemo;
   }
 
   
   public String getThumbnailPath() {
return this.thumbnailPath;
   }
 
   
   public void setThumbnailPath(String thumbnailPath) {
this.thumbnailPath = thumbnailPath;
   }
   
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
   public Date getTs() {
return this.ts;
   }
 
   
   public void setTs(Date ts) {
this.ts = ts;
   }
 
   
   public Integer getValue() {
     return this.value;
   }
 
   
   public void setValue(Integer value) {
     this.value = value;
   }
 
   
   public boolean isNew() {
     Date lastDays = DateUtil.addDays(DateUtil.now(), -LAST_DAYS);
     return getTs().after(lastDays);
   }
 }


