package com.awign.getaway.session;

/**
 * Created by nitesh on 14/3/17.
 */
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
public class SessionVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(SessionVerticle.class);

  private Router appRouter = null;

  public SessionVerticle(Router _router){
    this.appRouter = _router;
  }

  @Override
  public void start() {

    LOG.info("-------    Starting ETAVerticle    -------");

    //Receiver sub-router
    Router sessionRouter = Router.router(vertx);

    //receiver route handler
    sessionRouter.post("/api/post").handler( routingContext -> {
      LOG.info("request to receiver endpoint.");
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type","application/json");
      JsonObject requestBody = routingContext.getBodyAsJson();

      response.end(Json.encodePrettily(requestBody));
    });

    //Mounting ETA Router on Application router after path '/eta'
    appRouter.mountSubRouter("/session",sessionRouter);

  }
}
