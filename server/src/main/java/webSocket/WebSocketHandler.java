package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    System.out.println(message);
    UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
    System.out.println(userGameCommand.toString());
//    switch (userGameCommand.getCommandType()) {
//      case JOIN_PLAYER ->
//    }
  }

//  private void join(String authToken, Session session){
//    connections.add(authToken,session);
//    var message = String.format("%s is joining the game",)
//  }

}