package handlers;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import service.DeleteService;
import service.UserService;
import spark.Request;
import spark.Response;

public class Register {
  public static Object handle(Request req, Response res) throws Exception{
    var user = new Gson().fromJson(req.body(), UserData.class);
    UserService service = new UserService();
    var authToken = service.addUser(user);
    return new Gson().toJson(authToken);
  }
}
