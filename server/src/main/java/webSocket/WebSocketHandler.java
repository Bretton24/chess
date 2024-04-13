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

  private void observe(String authToken, Session session) throws IOException {
    connections.add(authToken,session);
    var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
    connections.broadcast(authToken,serverMessage);
  }

}