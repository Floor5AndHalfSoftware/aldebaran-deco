package com.aldebaran.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;









@Entity
@SqlResultSetMapping(name = "TopGamesResult", entities = {@EntityResult(entityClass = com.aldebaran.model.TopGamesResult.class, fields = {@FieldResult(name = "id", column = "id"), @FieldResult(name = "gameId", column = "gameId"), @FieldResult(name = "name", column = "name"), @FieldResult(name = "value", column = "value")})})
public class TopGamesResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private Integer gameId;
  private String name;
  private Integer value;
  
  public String getId() {
     return this.id;
  }
  
  public Integer getGameId() {
     return this.gameId;
  }

  
  public String getName() {
     return this.name;
  }
  
  public Integer getValue() {
     return this.value;
  }
}


