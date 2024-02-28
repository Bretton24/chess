package handlers;

import com.google.gson.Gson;
import dataAccess.*;
import model.Message;
import service.DeleteService;
import spark.Request;
import spark.Response;

public class Clear {

  Clear(){}
  public static Object handle(Request req, Response res){
    DeleteService service = new DeleteService();
    try{
      service.deleteDatabase();
      res.status(200);
      return "";
    }
    catch(DataAccessException e){
      res.status(500);
      var mess = new Message(e.getMessage());
      return new Gson().toJson(mess);
    }

  }

}
