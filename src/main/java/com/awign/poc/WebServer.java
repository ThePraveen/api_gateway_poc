package com.awign.poc;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

/**
 * Created by nitesh on 11/3/17.
 */
public class WebServer {
  static HttpServer webServer = null;
  static Vertx vertx = null;
  private WebServer(Vertx _vertx, JsonObject _config){
    vertx = _vertx;
    webServer = vertx.createHttpServer();
  }

  public static void initialize(Vertx _vertx, JsonObject _config){
    if (vertx != null && webServer != null){
      return;
    }
    new WebServer(_vertx, _config);
  }

  public static HttpServer getInstance() throws UninitializedException{
    if (webServer == null || vertx == null){
      throw new UninitializedException();
    }
    return webServer;
  }

  public static class UninitializedException extends Exception{
    UninitializedException(){
      super("Call to WebServer.initalize() hasn't been made before calling WebServer.getInstance()");
    }
  }
}
