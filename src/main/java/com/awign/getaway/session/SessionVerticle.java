package com.awign.getaway.session;

/**
 * Created by nitesh on 14/3/17.
 */
import com.awign.getaway.common.DBRedis;
import com.awign.getaway.session.models.Session;
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

    LOG.info("-------    Starting SessionVerticle    -------");

    //Receiver sub-router
    Router sessionRouter = Router.router(vertx);

    //receiver route handler
    sessionRouter.post("/api/save").handler( routingContext -> {
      LOG.info("request to save session.");
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type","application/json");
      JsonObject requestBody = routingContext.getBodyAsJson();

      Session session = new Session(requestBody.getString("key"), requestBody.getString("value"));
      Boolean status = false;
      try {
        status = session.save();
      } catch (DBRedis.InitializeException e) {
        e.printStackTrace();
      }
      response.end(Json.encodePrettily(status));
    });

    sessionRouter.get("/api/fetch/:key").handler( routingContext -> {
      LOG.info("request to save session.");
      HttpServerResponse response = routingContext.response();
      String key = routingContext.request().getParam("key");
      response.putHeader("content-type","application/json");

      Session session = new Session(key);
      Boolean status = false;
      try {
        session.getOne();
      } catch (DBRedis.InitializeException e) {
        e.printStackTrace();
      }
      response.end(Json.encodePrettily(status));
    });

    //Mounting ETA Router on Application router after path '/eta'
    appRouter.mountSubRouter("/session",sessionRouter);

  }
}
