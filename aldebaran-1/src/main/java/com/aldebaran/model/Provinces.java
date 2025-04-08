package com.aldebaran.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;






@Entity
@SqlResultSetMapping(name = "Provinces", entities = {@EntityResult(entityClass = com.aldebaran.model.Provinces.class, fields = {@FieldResult(name = "id", column = "id"), @FieldResult(name = "name", column = "name")})})
public class Provinces
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  private Integer id;
  private String name;
  
  public Integer getId() {
     return this.id;
  }
  
  public void setId(Integer id) {
     this.id = id;
  }
  
  public String getName() {
     return this.name;
  }
  
  public void setName(String name) {
     this.name = name;
  }
}


