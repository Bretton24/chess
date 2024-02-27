package handlers;

import com.google.gson.Gson;
import dataAccess.UnauthorizedAccessException;
import model.GameID;
import model.GameName;
import model.message;
import service.GameService;
import spark.Request;
import spark.Response;

public class ListGames {

  public static Object handle(Request req, Response res){
    var authToken = req.headers("Authorization");
    GameService service = new GameService();
    try{
      GameID newGame = service.listGames(authToken,game);
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
