package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER -> join(userGameCommand.getAuthString(),session);
      case JOIN_OBSERVER -> observe(userGameCommand.getAuthString(), session);
    }
  }

  private void join(String authToken, Session session) throws IOException {
    connections.add(authToken,session);
    var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
    connections.broadcast(authToken,serverMessage);
  }

  private void observe(String authToken, Session session) throws IOException {
    connections.add(authToken,session);
    var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
    connections.broadcast(authToken,serverMessage);
  }

}