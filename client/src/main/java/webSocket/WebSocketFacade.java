package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;
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
              System.out.println(loadGame.getServerMessageType());
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

  public void observeGame(String authToken, Integer gameID) throws Exception{
    try {
      var joinObserver = new JoinObserver(authToken,gameID);
      joinObserver.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
      this.session.getBasicRemote().sendText(new Gson().toJson(joinObserver));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  public void move(String authToken, int gameID, ChessMove chessMove) throws Exception{
    try {
      var makeMove = new MakeMove(authToken,gameID,chessMove);
      makeMove.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
      this.session.getBasicRemote().sendText(new Gson().toJson(makeMove));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
