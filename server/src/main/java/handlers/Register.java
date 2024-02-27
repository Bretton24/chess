package handlers;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import service.DeleteService;
import service.UserService;
import spark.Request;
import spark.Response;
import Request.UserReq;

public class Register {
  public static Object handle(Request req, Response res) throws Exception{
    var request = new UserReq();
    var user = request.createUser(req);
    UserService service = new UserService();
    var authToken = service.addUser(user);
    return new Gson().toJson(authToken);
  }
}
