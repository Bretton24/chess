package handlers;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UnauthorizedAccessException;
import model.Message;
import service.GameService;
import spark.Request;
import spark.Response;

public class ListGames {

  public static Object handle(Request req, Response res){
    var authToken = req.headers("Authorization");
    GameService service = new GameService();
    try{
      var games = service.listGames(authToken);
      res.status(200);
      return new Gson().toJson(games);
    }
    catch(UnauthorizedAccessException e){
      res.status(401);
      var mess = new Message(e.getMessage());
      return new Gson().toJson(mess);
    }
  }
}
