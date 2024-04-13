package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.GameDAO;
import dataAccess.SQLGameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.AuthService;
import service.GameService;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
    UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER: {
        JoinPlayer joinPlayer = new Gson().fromJson(message,JoinPlayer.class);
        join(joinPlayer.getAuthString(), joinPlayer.getGameID(), joinPlayer.getPlayerColor(),session);
        break;
      }
      case JOIN_OBSERVER: {
        JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
        observe(joinObserver.getAuthString(), joinObserver.getGameID(), session);
        break;
      }
    }
  }

  private void join(String authToken, Integer gameID, ChessGame.TeamColor teamColor, Session session) throws Exception {
    connections.add(authToken,session);
    GameData game = null;
    UserData user = null;
    try {
      game=GameService.gameAccess.getGame(gameID);
      if(!AuthService.authAccess.authTokenPresent(authToken)){
        Error error = new Error("Error: unauthorized access");
        connections.respondToSender(authToken,error);
        return;
      }
      user = AuthService.authAccess.getUser(authToken);
      if (teamColor == ChessGame.TeamColor.WHITE && !game.whiteUsername().equals(user.username())){
        Error error = new Error("Error: team taken");
        connections.respondToSender(authToken,error);
        return;
      }
      if (teamColor == ChessGame.TeamColor.BLACK && !game.blackUsername().equals(user.username())){
        Error error = new Error("Error: team taken");
        connections.respondToSender(authToken,error);
        return;
      }
    }catch(Exception e){
      Error error = new Error("Error: game does not exist");
      connections.respondToSender(authToken,error);
      return;
    }
      LoadGame game1 = new LoadGame(game.game());
      String message = String.format("%s joined the game as %s",user.username(),teamColor);
      Notification notification = new Notification(message);
      connections.respondToSender(authToken,game1);
      connections.broadcast(authToken,notification);
  }

  private void observe(String authToken, Integer gameID, Session session) throws Exception {
    connections.add(authToken,session);
    GameData game = null;
    game = GameService.gameAccess.getGame(gameID);
    if (game == null){
      Error error = new Error("Error: game does not exist");
      connections.respondToSender(authToken,error);
      return;
    }
    if(!AuthService.authAccess.authTokenPresent(authToken)){
      Error error = new Error("Error: unauthorized access");
      connections.respondToSender(authToken,error);
      return;
    }
    UserData user = AuthService.authAccess.getUser(authToken);
    LoadGame loadGame = new LoadGame(game.game());
    connections.respondToSender(authToken,loadGame);
    String message = String.format("%s joined the game as observer",user.username());
    Notification notification = new Notification(message);
    connections.broadcast(authToken,notification);
  }
}