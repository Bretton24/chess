package Request;

import model.AuthData;
import spark.Request;

public class AuthReq extends BaseReq {
  public static AuthData createAuth(Request req){
    String requestString = stringify(req);
    return serial.fromJson(requestString,AuthData.class);
  }
}
