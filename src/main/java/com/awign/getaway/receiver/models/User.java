package com.awign.getaway.receiver.models;

import com.awign.getaway.common.DBMongo;
import com.awign.getaway.common.DBWrapper;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by nitesh on 11/3/17.
 */
public class User implements DBWrapper {
  public static final String MONGO_COLLECTION = "user";
  private String id;
  private String name;

  public User(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean save() throws DBMongo.InitializeException {
    JsonObject jsonObject = new JsonObject(Json.encode(this));
    DBMongo.getInstance().save(MONGO_COLLECTION, jsonObject, event -> {
      if (event.succeeded()) {
        String id = event.result();
        System.out.println("Saved User with id " + id);
      } else {
        event.cause().printStackTrace();
      }
    });
    return false;
  }

  @Override
  public boolean update() throws DBMongo.InitializeException{
    return false;
  }

  @Override
  public boolean delete() throws DBMongo.InitializeException{
    return false;
  }

  @Override
  public DBWrapper getOne() throws DBMongo.InitializeException {
    return null;
  }

  @Override
  public List<DBWrapper> getAll() throws DBMongo.InitializeException {
    return null;
  }
}
