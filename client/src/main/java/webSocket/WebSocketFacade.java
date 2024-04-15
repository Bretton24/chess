package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import client.ChessClient;
import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import model.UserData;
import ui.ChessboardDrawing;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  ServerMessageHandler  serverMessageHandler;
  UserData user;
  ChessClient client;
  ChessboardDrawing drawing = new ChessboardDrawing();
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
              System.out.println(error.getErrorMessage());
              break;
            }
            case LOAD_GAME:{
              LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
              var game = loadGame.getGame();
              drawing.updateGame(game.game());
              if (game.blackUsername() != null && game.blackUsername().equals(user.username())){
                drawing.drawChessboard(true,null);
              }
              else if (game.whiteUsername().equals(user.username()) || (game.whiteUsername() != null && game.blackUsername() != null)){
                drawing.drawChessboard(false,null);
              }
              break;
            }
          }
        };
      });
    ;}catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  public void joinGame(String authToken, int gameID, ChessGame.TeamColor color,UserData user) throws Exception{
    try {
      this.user = user;
      var joinPlayer = new JoinPlayer(authToken,gameID,color);
      joinPlayer.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
      this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  public void observeGame(String authToken, Integer gameID,UserData user) throws Exception{
    try {
      this.user = user;
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

  public void resign(String authToken, int gameID) throws Exception {
    var resignPerson = new Resign(authToken,gameID);
    resignPerson.setCommandType(UserGameCommand.CommandType.RESIGN);
    this.session.getBasicRemote().sendText(new Gson().toJson(resignPerson));
  }

  public void leave(String authToken, int gameID) throws Exception{
    try {
      var leave = new Leave(authToken,gameID);
      leave.setCommandType(UserGameCommand.CommandType.LEAVE);
      this.session.getBasicRemote().sendText(new Gson().toJson(leave));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
