package com.aldebaran.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties(ignoreUnknown = true)
public class Categories
{
  private String category;
  private String type;
  private boolean enabled;
  private String users_id;
  
  public String getCategory() {
     return this.category;
  }
  
  public void setCategory(String category) {
     this.category = category;
  }
  
  public String getType() {
     return this.type;
  }
  
  public void setType(String type) {
     this.type = type;
  }
  
  public boolean isEnabled() {
     return this.enabled;
  }
  
  public void setEnabled(boolean enabled) {
     this.enabled = enabled;
  }
  
  public String getUsers_id() {
     return this.users_id;
  }
  
  public void setUsers_id(String usersId) {
     this.users_id = usersId;
  }
}


