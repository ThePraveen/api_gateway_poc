package com.awign.poc.common;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by nitesh on 11/3/17.
 */
public class DBMongo {

  private static MongoClient mongoClient;
  private static Vertx vertx;

  private DBMongo(Vertx vertx, JsonObject config){
    DBMongo.vertx = vertx;
    mongoClient = MongoClient.createShared(vertx, config);
  }

  public static void initialize(Vertx vertx, JsonObject config){
    new DBMongo(vertx,config);
  }
  public static  MongoClient getInstance() throws InitializeException{
    if (mongoClient == null){
      throw new InitializeException("DBMongo.initialize() not called in the main vertical!");
    }
    return mongoClient;
  }

  public static class InitializeException extends Exception{
    public InitializeException(String message){
      super(message);
    }
  }
}
