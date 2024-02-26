package handlers;

import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import service.DeleteService;
import spark.Request;
import spark.Response;

public class Clear {

  public static Object handle(Request request, Response response){
    DeleteService service = new DeleteService();
    service.deleteDatabase();
    response.status(200);
    return "";
  }
}
