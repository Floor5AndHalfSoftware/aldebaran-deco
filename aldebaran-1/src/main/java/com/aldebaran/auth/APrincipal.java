package com.aldebaran.auth;

import java.security.Principal;







public class APrincipal
  implements Principal
{
  private Integer id;
  private String firstname;
  private String lastname;
  private String email;
  private String userId;
  private String sessionId;
  private String sportsBookToken;
  private String lastlogin;
  private Integer status;
  private long ts;
  private String jsessionid;
  
  public APrincipal(Integer id, String firstname, String lastname, String email, String userId, String sessionId, String sportsBookToken, String lastlogin, Integer status, String jsessionid) {
     this.id = id;
     this.firstname = firstname;
     this.lastname = lastname;
     this.email = email;
     this.userId = userId;
     this.sessionId = sessionId;
     this.sportsBookToken = sportsBookToken;
     this.lastlogin = lastlogin;
     this.status = status;
     this.ts = System.currentTimeMillis();
     this.jsessionid = jsessionid;
  }
  
  public Integer getId() {
     return this.id;
  }
  
  public String getFirstname() {
     return this.firstname;
  }
  
  public String getLastname() {
     return this.lastname;
  }
  
  public String getEmail() {
     return this.email;
  }
  
  public String getUserId() {
     return this.userId;
  }
  
  public String getSessionId() {
     return this.sessionId;
  }
  
  public long getTs() {
     return this.ts;
  }
  
  public String getSportsBookToken() {
     return this.sportsBookToken;
  }
  
  public String getLastlogin() {
     return this.lastlogin;
  }
  
  public Integer getStatus() {
     return this.status;
  }

  
  public String getName() {
     return getEmail();
  }
  
  public String getJsessionid() {
     return this.jsessionid;
  }
}


