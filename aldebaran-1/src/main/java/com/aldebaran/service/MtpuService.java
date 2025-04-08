package com.aldebaran.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Service
public class MtpuService
{
  @Value("${mtpu.urls.main}")
  private String url;
  @Value("${mtpu.urls.launch}")
  private String urlLaunch;
  @Value("${mtpu.username}")
  private String username;
  @Value("${mtpu.password}")
  private String password;
  
  public String getUsername() {
     return this.username;
  }
  public void setUsername(String username) {
     this.username = username;
  }
  public String getPassword() {
     return this.password;
  }
  public void setPassword(String password) {
     this.password = password;
  }
  public String getUrl() {
     return this.url;
  }
  public void setUrl(String url) {
     this.url = url;
  }
  public String getUrlLaunch() {
     return this.urlLaunch;
  }
}


