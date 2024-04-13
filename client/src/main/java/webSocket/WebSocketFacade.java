package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  ServerMessageHandler  serverMessageHandler;
  public WebSocketFacade(String url,ServerMessageHandler serverMessageHandler) throws Exception {
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.serverMessageHandler = serverMessageHandler;

      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      this.session = container.connectToServer(this, socketURI);
      //set message handler
      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          ServerMessage serverMessage=new Gson().fromJson(message, ServerMessage.class);
          switch (serverMessage.getServerMessageType()){
            case NOTIFICATION: {
              Notification notification = new Gson().fromJson(message, Notification.class);
              System.out.println(notification.getMessage());
              break;
            }
            case ERROR: {
              Error error = new Gson().fromJson(message, Error.class);
              System.out.println(error.getServerMessageType());
              break;
            }
            case LOAD_GAME:{
              LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
              loadGame.getGame();

              break;
            }
          }
        };
      });
    ;}catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  public void joinGame(String authToken, int gameID, ChessGame.TeamColor color) throws Exception{
    try {
      var joinPlayer = new JoinPlayer(authToken,gameID,color);
      joinPlayer.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
      this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  public void observeGame(String authToken) throws Exception{
    try {
      var client = new UserGameCommand(authToken);
      System.out.println(client.getCommandType());
      this.session.getBasicRemote().sendText(new Gson().toJson(client));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
