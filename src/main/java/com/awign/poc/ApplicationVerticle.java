package com.awign.poc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Created by nitesh on 11/3/17.
 */
public class ApplicationVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationVerticle.class);

  private Router appRouter = null;

  public ApplicationVerticle(Router _router){
    this.appRouter = _router;
  }

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
  }

  @Override
  public void start() {

    LOG.info("-------    Starting ApplicationVerticle    -------");

    //Base Routes to be handled by the Application Verticle Class. This might serve a web interface in future
    appRouter.route("/").handler( rcx -> {
      rcx.response().setStatusCode(200).end("Awign Getaway");
    });
    appRouter.route("/health").handler( rcx -> {
      rcx.response().setStatusCode(200).end("OK");
    });

  }
}
