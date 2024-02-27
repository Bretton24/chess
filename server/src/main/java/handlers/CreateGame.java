package handlers;

import com.google.gson.Gson;
import dataAccess.UnauthorizedAccessException;
import model.*;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Request;

public class CreateGame {

  public static Object handle(Request req, Response res){
    var game = new Gson().fromJson(req.body(), GameName.class);
    var authToken = req.headers("Authorization");
    GameService service = new GameService();
    try{
      GameID newGame = service.createGame(authToken,game);
      res.status(200);
      return new Gson().toJson(newGame);
    }
    catch(UnauthorizedAccessException e){
      res.status(401);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
  }
}