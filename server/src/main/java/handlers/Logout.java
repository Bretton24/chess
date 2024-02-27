package handlers;

import service.DeleteService;
import spark.Request;
import spark.Response;

public class Logout {

  public static Object handle(Request req, Response res) throws Exception{
    DeleteService service = new DeleteService();
    service.deleteDatabase();
    res.status(200);
    return "\"\"";
  }
}
