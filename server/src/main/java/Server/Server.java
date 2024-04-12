package server;

import dataAccess.DatabaseManager;
import handlers.*;
import service.Services;
import spark.*;
import webSocket.WebSocketHandler;


public class Server {

  public int run(int desiredPort) {
    Spark.port(desiredPort);

    Spark.staticFiles.location("web");
    WebSocketHandler webSocketHandler = new WebSocketHandler();
    Spark.webSocket("/connect",webSocketHandler);


    Spark.delete("/db", Clear::handle);
    Spark.post("/user", Register::handle);
    Spark.post("/session", Login::handle);
    Spark.delete("/session", Logout::handle);
    Spark.post("/game", CreateGame::handle);
    Spark.get("/game",ListGames::handle);
    Spark.put("/game",JoinGame::handle);


    Spark.awaitInitialization();
    return Spark.port();
  }

  public void stop() {
    Spark.stop();
    Spark.awaitStop();
  }
}