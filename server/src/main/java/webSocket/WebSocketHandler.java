package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.GameDAO;
import dataAccess.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
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
    try{
      GameData game =GameService.gameAccess.getGame(gameID);
    }catch(Exception e){
      ServerMessage error = new Error("Error: game does not exist");
      connections.respondToSender(authToken,error);
    }



  }

  private void observe(String authToken, Session session) throws IOException {
    connections.add(authToken,session);
    var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
    connections.broadcast(authToken,serverMessage);
  }

}