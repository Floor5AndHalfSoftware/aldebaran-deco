package com.aldebaran.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class GameParams
{
  private String gscode;
  private String enableOnlyFor;
  private String orderPopular;
  private String popularOrder;
  private String order;
  private String largeView;
  private String viewInBanner;
  
  public String getGscode() {
     return this.gscode;
  }
  public void setGscode(String gscode) {
     this.gscode = gscode;
  }
  public String getEnableOnlyFor() {
     return this.enableOnlyFor;
  }
  public void setEnableOnlyFor(String enableOnlyFor) {
     this.enableOnlyFor = enableOnlyFor;
  }
  public String getOrderPopular() {
     return this.orderPopular;
  }
  public void setOrderPopular(String orderPopular) {
     this.orderPopular = orderPopular;
  }
  public String getOrder() {
     return this.order;
  }
  public void setOrder(String order) {
     this.order = order;
  }
  public String getPopularOrder() {
     return this.popularOrder;
  }
  public void setPopularOrder(String popularOrder) {
     this.popularOrder = popularOrder;
  }
  public String getLargeView() {
     return this.largeView;
  }
  public void setLargeView(String largeView) {
     this.largeView = largeView;
  }
  public String getViewInBanner() {
     return this.viewInBanner;
  }
  public void setViewInBanner(String viewInBanner) {
     this.viewInBanner = viewInBanner;
  }
}


