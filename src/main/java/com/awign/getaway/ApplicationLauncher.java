package com.awign.getaway;

import com.awign.getaway.common.DBRedis;
import com.awign.getaway.session.SessionVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by nitesh on 11/3/17.
 */
public class ApplicationLauncher{

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationLauncher.class);


  public static void main(String... args)  {
    LOG.info("Starting Awign Application Getaway Application");
    Vertx vertx = Vertx.vertx();

    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setConfig(getConfiguration());

    //Application Server
    WebServer.initialize(vertx, deploymentOptions.getConfig());

    //Application Router
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route().handler(CookieHandler.create());

    //DB and Services initialization
    LOG.info(String.format("Initializing DB Connection with config : %s",deploymentOptions.getConfig().toString()));
    DBRedis.initialize(vertx, deploymentOptions.getConfig());

    //Deploy all the verticles here
    vertx.deployVerticle(new ApplicationVerticle(router));
    vertx.deployVerticle(new SessionVerticle(router));

    try {
      //Starting Server
      LOG.info("listening on port 8080");
      //Below Line should be at the last for mounting the router to the server
      WebServer.getInstance().requestHandler(router::accept).listen(8080);
    } catch (WebServer.UninitializedException e) {
      e.printStackTrace();
    }

  }


  private static JsonObject getConfiguration() {
    File configFile = new File("config/config.json");
    JsonObject conf = new JsonObject();
    if (configFile.isFile()) {
      System.out.println("Reading config file: " + configFile.getAbsolutePath());
      try (Scanner scanner = new Scanner(configFile).useDelimiter("\\A")) {
        String sconf = scanner.next();
        try {
          conf = new JsonObject(sconf);
        } catch (DecodeException e) {
          System.err.println("Configuration file " + sconf + " does not contain a valid JSON object");
        }
      } catch (FileNotFoundException e) {
        // Ignore it.
      }
    } else {
      System.out.println("Config file not found " + configFile.getAbsolutePath());
    }
    return conf;
  }
}
