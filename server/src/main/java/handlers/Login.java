package handlers;

import com.google.gson.Gson;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class Login {
  public static Object handle(Request req, Response res) throws Exception{

    var user = new Gson().fromJson(req.body(), UserData.class);
    UserService service = new UserService();
    var authToken = service.loginUser(user);
    return new Gson().toJson(authToken);
  }
}
