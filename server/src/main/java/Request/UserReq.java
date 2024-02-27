package Request;

import model.GameData;
import model.UserData;
import spark.Request;

public class UserReq extends BaseReq{
  public static UserData createUser(Request req){
    String requestString = stringify(req);
    return serial.fromJson(requestString, UserData.class);
  }
}
