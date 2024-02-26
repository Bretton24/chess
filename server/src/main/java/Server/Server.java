package server;

import handlers.Clear;
import handlers.Login;
import handlers.Register;
import spark.*;


public class Server {

  public int run(int desiredPort) {
    Spark.port(desiredPort);

    Spark.staticFiles.location("web");

    Spark.delete("/db", Clear::handle);
    Spark.post("/user", Register::handle);
    Spark.post("/session", Login::handle);


    Spark.awaitInitialization();
    return Spark.port();
  }

  public void stop() {
    Spark.stop();
    Spark.awaitStop();
  }
}