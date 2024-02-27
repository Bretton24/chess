package handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import model.UserData;
import model.message;
import service.UserService;
import spark.Request;
import spark.Response;

public class Register {
  public static Object handle(Request req, Response res){
    var user = new Gson().fromJson(req.body(), UserData.class);
    UserService service = new UserService();
    try{
      var authToken = service.addUser(user);
      res.status(200);
      return new Gson().toJson(authToken);
    }
    catch(DuplicateException e){
      res.status(403);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
    catch(BadRequestException e){
      res.status(400);
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }
    catch(DataAccessException e){
      var mess = new message(e.getMessage());
      return new Gson().toJson(mess);
    }

  }



}
