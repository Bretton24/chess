package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedAccessException;
import model.AuthData;
import model.UserData;
import model.message;
import service.AuthService;
import service.DeleteService;
import service.Services;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;

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
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
    catch(DataAccessException e){
      res.status(500);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }

  }
}
