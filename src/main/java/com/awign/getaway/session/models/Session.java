package com.awign.getaway.session.models;

import com.awign.getaway.common.DBRedis;
import com.awign.getaway.common.DBRedis;
import com.awign.getaway.common.DBWrapper;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import javax.swing.*;
import java.util.List;

/**
 * Created by nitesh on 14/3/17.
 */
public class Session  implements DBWrapper {
  private String key;
  private String value;

  public Session(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public Session(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean save() throws DBRedis.InitializeException {
    DBRedis.getInstance().set(this.getKey(), this.getValue(), event -> {
      if (event.succeeded()) {
        System.out.println("Saved Session with id " + this.getKey());
      } else {
        event.cause().printStackTrace();
      }
    });
    return true;
  }

  @Override
  public boolean update() throws DBRedis.InitializeException{
    return false;
  }

  @Override
  public boolean delete() throws DBRedis.InitializeException{
    return false;
  }

  @Override
  public DBWrapper getOne() throws DBRedis.InitializeException {
    DBRedis.getInstance().get(this.getKey(), event -> {
      if (event.succeeded()) {
        System.out.println("Session for id : " + event.result());
      } else {
        event.cause().printStackTrace();
      }
    });
    return null;
  }

  @Override
  public List<DBWrapper> getAll() throws DBRedis.InitializeException {
    return null;
  }
}
