package handlers;

import com.google.gson.Gson;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedAccessException;
import model.GameID;
import model.GameName;
import model.PlayerInfo;
import model.message;
import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGame {
  public static Object handle(Request req, Response res){
    var playerInfo = new Gson().fromJson(req.body(), PlayerInfo.class);
    var authToken = req.headers("Authorization");
    GameService service = new GameService();
    try{
      service.joinGame(authToken,playerInfo);
      res.status(200);
      return "";
    }
    catch(UnauthorizedAccessException e){
      res.status(401);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
    catch(DuplicateException e){
      res.status(403);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
  }
}
