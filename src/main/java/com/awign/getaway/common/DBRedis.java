package com.awign.getaway.common;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
/**
 * Created by nitesh on 14/3/17.
 */
public class DBRedis {


  private static RedisClient redisClient;
  private static Vertx vertx;

  private DBRedis(Vertx vertx, JsonObject appConfig){
    RedisOptions config = new RedisOptions().setHost(appConfig.getString("host"));
    redisClient = RedisClient.create(vertx, config);
  }

  public static void initialize(Vertx vertx, JsonObject appConfig){
    new DBRedis(vertx, appConfig);
  }
  public static  RedisClient getInstance() throws DBRedis.InitializeException {
    if (redisClient == null){
      throw new DBRedis.InitializeException("DBRedis.initialize() not called in the main vertical!");
    }
    return redisClient;
  }

  public static class InitializeException extends Exception{
    public InitializeException(String message){ super(message); }
  }
}
