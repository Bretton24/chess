package server;

import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.util.logging.Handler;

public class ServerFacade {
  private final String serverUrl;

  public ServerFacade(String url){
    serverUrl = url;
  }

//  Spark.delete("/db", Clear::handle);
//    Spark.post("/user", Register::handle);
//    Spark.post("/session", Login::handle);
//    Spark.delete("/session", Logout::handle);
//    Spark.post("/game", CreateGame::handle);
//    Spark.get("/game",ListGames::handle);
//    Spark.put("/game",JoinGame::handle);

  public AuthData addUser(UserData user) throws Exception{
    var path = "/user";
    return this.makeRequest("POST",path,user,AuthData.class,null);
  }

  public AuthData loginUser(UserData user) throws Exception{
    var path = "/session";
    return this.makeRequest("POST",path,user,AuthData.class,null);
  }

  public void logoutUser(AuthData authToken) throws Exception{
    var path = "/session";
    this.makeRequest("DELETE",path,null,null,authToken.authToken());
  }

  public GameID gameCreate(AuthData authToken,String name) throws Exception{
    var game = new GameName(name);
    var path = "/game";
    return this.makeRequest("POST",path,game, GameID.class,authToken.authToken());
  }

  public GameList list(AuthData authToken) throws Exception{
    var path = "/game";
    return this.makeRequest("GET",path,null,GameList.class,authToken.authToken());
  }

  public void joinGame(AuthData authToken,PlayerInfo playerInfo) throws Exception{
    var path = "/game";
    this.makeRequest("PUT",path,PlayerInfo.class,null,authToken.authToken());
  }

  private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception{
    try{
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);
      if (authToken != null){
        writeBody(request,http,authToken);
      }
      else {
        writeBody(request, http, null);
      }
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http,responseClass);
    } catch (Exception ex){
      throw new Exception(ex.getMessage());
    }
  }

  private static void writeBody(Object request, HttpURLConnection http, String authToken) throws IOException{
    if (request != null || authToken != null){
      http.addRequestProperty("Content-Type","application/json");
      if (authToken != null){
        http.setRequestProperty("Authorization",authToken);
      }
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()){
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException,Exception{
    var status = http.getResponseCode();
    if (!isSuccessful(status)){
      throw new Exception("failure: " + status);
    }
  }

  private static  <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException{
    T response = null;
    if (http.getContentLength() < 0){
      try (InputStream respBody = http.getInputStream()){
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null){
          response = new Gson().fromJson(reader,responseClass);
        }
      }
    }
    return response;
  }

  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
