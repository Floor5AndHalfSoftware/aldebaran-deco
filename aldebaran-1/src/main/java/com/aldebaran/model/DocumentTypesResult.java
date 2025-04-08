package com.aldebaran.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;









@Entity
@SqlResultSetMapping(name = "DocumentTypesResult", entities = {@EntityResult(entityClass = com.aldebaran.model.DocumentTypesResult.class, fields = {@FieldResult(name = "id", column = "id"), @FieldResult(name = "code", column = "code"), @FieldResult(name = "description", column = "description"), @FieldResult(name = "regularExpression", column = "regularExpression"), @FieldResult(name = "lang", column = "lang")})})
public class DocumentTypesResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  private Integer id;
  private String code;
  private String description;
  private String regularExpression;
  private String lang;
  
  public Integer getId() {
     return this.id;
  }
  
  public void setId(Integer id) {
     this.id = id;
  }
  
  public String getCode() {
     return this.code;
  }
  
  public void setCode(String code) {
     this.code = code;
  }
  
  public String getDescription() {
     return this.description;
  }
  
  public void setDescription(String description) {
     this.description = description;
  }
  
  public String getRegularExpression() {
     return this.regularExpression;
  }
  
  public void setRegularExpression(String regularExpression) {
     this.regularExpression = regularExpression;
  }
  
  public String getLang() {
     return this.lang;
  }
  
  public void setLang(String lang) {
     this.lang = lang;
  }
}


