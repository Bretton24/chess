package Request;

import model.AuthData;
import model.GameData;
import spark.Request;

public class GameReq extends BaseReq {
  public static GameData createGame(Request req){
    String requestString = stringify(req);
    return serial.fromJson(requestString,GameData.class);
  }
}
