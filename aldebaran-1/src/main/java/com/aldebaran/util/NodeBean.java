package com.aldebaran.util;

public class NodeBean
{
  private int result;
  private String description;
  
  public NodeBean() {}
  
  public NodeBean(int result, String description) {
     this.result = result;
     this.description = description;
  }
  
  public int getResult() {
     return this.result;
  }
  
  public void setResult(int result) {
     this.result = result;
  }
  
  public String getDescription() {
     return this.description;
  }
  
  public void setDescription(String description) {
     this.description = description;
  }
}


