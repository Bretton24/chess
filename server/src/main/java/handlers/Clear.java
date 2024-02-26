package handlers;

import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import org.eclipse.jetty.client.HttpResponseException;
import service.DeleteService;
import spark.Request;
import spark.Response;

public class Clear {

  Clear(){}
  public static Object handle(Request req, Response res) throws Exception{
    DeleteService service = new DeleteService();
    service.deleteDatabase();
    res.status(200);
    return "";
  }

}
