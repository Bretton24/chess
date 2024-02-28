package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedAccessException;
import model.Message;
import service.AuthService;
import spark.Request;
import spark.Response;

public class Logout {

  public static Object handle(Request req, Response res) throws UnauthorizedAccessException , DataAccessException{
    AuthService service = new AuthService();
    try{
      service.logoutUser(req.headers("Authorization"));
      res.status(200);
      return "";
    }
    catch(UnauthorizedAccessException e){
      res.status(401);
      var mess = new Message(e.getMessage());
      return new Gson().toJson(mess);
    }
    catch(DataAccessException e){
      res.status(500);
      var mess = new Message(e.getMessage());
      return new Gson().toJson(mess);
    }

  }
}
