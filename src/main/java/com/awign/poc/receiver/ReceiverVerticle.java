package com.awign.poc.receiver;

import com.awign.poc.common.DBMongo;
import com.awign.poc.receiver.models.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by nitesh on 11/3/17.
 */
public class ReceiverVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(ReceiverVerticle.class);

  private Router appRouter = null;

  public ReceiverVerticle(Router _router){
    this.appRouter = _router;
  }

  @Override
  public void start() {

    LOG.info("-------    Starting ETAVerticle    -------");

    //Receiver sub-router
    Router receiverRouter = Router.router(vertx);

    //receiver route handler
    receiverRouter.post("/api/post").handler( routingContext -> {
      LOG.info("request to receiver endpoint.");
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type","application/json");
      JsonObject requestBody = routingContext.getBodyAsJson();
      System.out.println(requestBody.getString("name"));

      User user = new User(requestBody.getString("name"));
      try {
        user.save();
      } catch (DBMongo.InitializeException e) {
        e.printStackTrace();
      }
      response.end(Json.encodePrettily(user));
    });

    //Mounting ETA Router on Application router after path '/eta'
    appRouter.mountSubRouter("/receiver",receiverRouter);

  }
}
