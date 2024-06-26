package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.SQLGameDAO;
import dataAccess.UnauthorizedAccessException;
import model.AuthData;
import model.GameData;
import model.PlayerInfo;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.security.core.parameters.P;
import service.AuthService;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;


@WebSocket
public class WebSocketHandler {

  private ConnectionManager connections = new ConnectionManager();
  public final ConcurrentHashMap<Integer, ConnectionManager> connManager = new ConcurrentHashMap<>();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
    UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER: {
        join(message,session);
        break;
      }
      case JOIN_OBSERVER: {
        observe(message, session);
        break;
      }
      case MAKE_MOVE: {
        move(message,session);
        break;
      }
      case LEAVE: {
        leave(message,session);
        break;
      }
      case RESIGN: {
        resign(message,session);
        break;
      }
    }
  }

  private void join(String message, Session session) throws Exception {
    JoinPlayer joinPlayer = new Gson().fromJson(message,JoinPlayer.class);
    var gameID = joinPlayer.getGameID();
    var authToken = joinPlayer.getAuthString();
    var teamColor = joinPlayer.getPlayerColor();
    if(connManager.containsKey(gameID)){
      connections = connManager.get(gameID);
    }
    else{
      connections = new ConnectionManager();
    }
    connections.add(authToken,session);
    connManager.put(gameID,connections);
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
    GameData updatedGame = null;
    if (teamColor == ChessGame.TeamColor.WHITE){
      updatedGame = new GameData(gameID,user.username(),game.blackUsername(),game.gameName(),game.game());
    }
    else if(teamColor == ChessGame.TeamColor.BLACK){
      updatedGame = new GameData(gameID,game.whiteUsername(),user.username(),game.gameName(),game.game());
    }
      LoadGame game1 = new LoadGame(updatedGame);
      String mess = String.format("%s joined the game as team %s",user.username(),teamColor);
      Notification notification = new Notification(mess);
      connections.respondToSender(authToken,game1);
      connections.broadcast(authToken,notification);
  }

  private void resign(String message, Session session) throws Exception {
    Resign resign = new Gson().fromJson(message, Resign.class);
    var gameID = resign.getGameID();
    var authToken = resign.getAuthString();

    connections = connManager.get(gameID);
    GameData game = null;
    game = GameService.gameAccess.getGame(gameID);
    if (game == null){
      Error error = new Error("Error: game does not exist");
      connections.respondToSender(authToken,error);
      return;
    }
    if (game.whiteUsername() == null && game.blackUsername() == null && game.gameName() == null && game.game() == null){
      Error error = new Error("Error: you already resigned");
      connections.respondToSender(authToken,error);
      return;
    }
    if(!AuthService.authAccess.authTokenPresent(authToken)){
      Error error = new Error("Error: unauthorized access");
      connections.respondToSender(authToken,error);
      return;
    }
    UserData user = AuthService.authAccess.getUser(authToken);
    if (game.blackUsername().equals(user.username()) || game.whiteUsername().equals(user.username())){
      var updatedGame = new GameData(game.gameID(),null,null,null,null);
      GameService.gameAccess.updateGame(gameID,updatedGame);
      String mess = String.format("%s resigned",user.username());
      Notification notification = new Notification(mess);
      connections.broadcast(authToken,notification);
      connections.respondToSender(authToken,notification);
      connections.remove(authToken);
      connManager.put(gameID,connections);
      return;
    }
    else{
      Error error = new Error("Error: you can't resign you're an observer");
      connections.respondToSender(authToken,error);
      return;
    }
  }
  private void observe(String message, Session session) throws Exception {
    JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
    var gameID = joinObserver.getGameID();
    var authToken = joinObserver.getAuthString();

    if(connManager.containsKey(gameID)){
      connections = connManager.get(gameID);
    }
    else{
      connections = new ConnectionManager();
    }
    connections.add(authToken,session);
    connManager.put(gameID,connections);
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
    LoadGame loadGame = new LoadGame(game);
    connections.respondToSender(authToken,loadGame);
    String mess = String.format("%s joined the game as observer",user.username());
    Notification notification = new Notification(mess);
    connections.broadcast(authToken,notification);
  }

  private void leave(String message, Session session) throws Exception {
    Leave leaveGame = new Gson().fromJson(message, Leave.class);
    var gameID = leaveGame.getGameID();
    var authToken = leaveGame.getAuthString();

    connections = connManager.get(gameID);
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
    if (game.blackUsername() != null && game.blackUsername().equals(user.username())){
       var updatedGame = new GameData(game.gameID(),game.whiteUsername(),null,game.gameName(),game.game());
       GameService.gameAccess.updateGame(gameID,updatedGame);
       String mess = String.format("%s left the game",user.username());
       Notification notification = new Notification(mess);
       connections.broadcast(authToken,notification);
       connections.remove(authToken);
       connManager.put(gameID,connections);
       return;
    }
    else if (game.whiteUsername() != null && game.whiteUsername().equals(user.username())){
      var updatedGame = new GameData(game.gameID(),null,game.blackUsername(),game.gameName(),game.game());
      GameService.gameAccess.updateGame(gameID,updatedGame);
      String mess = String.format("%s left the game",user.username());
      Notification notification = new Notification(mess);
      connections.broadcast(authToken,notification);
      connections.remove(authToken);
      connManager.put(gameID,connections);
      return;
    }
    else{
      String mess = String.format("%s left the game",user.username());
      Notification notification = new Notification(mess);
      connections.broadcast(authToken,notification);
      connections.remove(authToken);
      connManager.put(gameID,connections);
      return;
    }
  }

  private void move(String message, Session session) throws Exception {
    MakeMove makeMove = new Gson().fromJson(message, MakeMove.class);
    var gameID = makeMove.getGameID();
    var authToken = makeMove.getAuthString();
    var chessMove = makeMove.getMove();
    connections = connManager.get(gameID);
    var game = GameService.gameAccess.getGame(gameID);
    var user = UserService.authAccess.getUser(authToken);
    if (game.blackUsername() != null && game.whiteUsername() != null){
        if(user.username().equals(game.blackUsername())){
          checkBlack(game,authToken,chessMove,gameID);
          return;
        }
        else if (user.username().equals(game.whiteUsername())){
          checkWhite(game,authToken,chessMove,gameID);
          return;
        }
        Error error = new Error("Error: you aren't even playing");
        connections.respondToSender(authToken,error);
        return;
    }
    Error error = new Error("Error: other player must join");
    connections.respondToSender(authToken,error);
    return;
  }


  private void checkWhite(GameData game,String authToken,ChessMove chessMove,Integer gameID) throws Exception{
    if (game.game().getTeamTurn().equals(ChessGame.TeamColor.BLACK)){
      Error error = new Error("Error: it's not your turn");
      connections.respondToSender(authToken,error);
      return;
    }
    else{
      if (game.game().getBoard().pieceAtPosition(chessMove.getStartPosition())){
        var piece = game.game().getBoard().getPiece(chessMove.getStartPosition());
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK){
          Error error = new Error("Error: not your teams piece");
          connections.respondToSender(authToken,error);
          return;
        }
        var moves = game.game().validMoves(chessMove.getStartPosition());
        if(moves.contains(chessMove)){
          game.game().makeMove(chessMove);
          LoadGame loadGame = new LoadGame(game);
          GameService.gameAccess.updateGame(gameID,game);
          connections.respondToSender(authToken,loadGame);
          connections.broadcast(authToken,loadGame);
          if (game.game().isInCheck(ChessGame.TeamColor.BLACK)){
            String message = String.format("%s is in check",game.blackUsername());
            Notification notification = new Notification(message);
            connections.broadcast(authToken,notification);
            connections.respondToSender(authToken,notification);
          }
          else if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
            String message = String.format("%s is in checkmate",game.blackUsername());
            isGameOver(game,authToken,gameID,message);
          }
          else if (game.game().isInStalemate(ChessGame.TeamColor.BLACK)){
            String message = String.format("%s is in stalemate",game.blackUsername());
            isGameOver(game,authToken,gameID,message);
          }
          else{
            String message = String.format("%s moved %s",game.whiteUsername(),chessMove.toString());
            createNotification(authToken,message);
          }
          return;
        }
        else{
          Error error = new Error("Error: not a valid move");
          connections.respondToSender(authToken,error);
          return;
        }
      }
      Error error = new Error("Error: no piece at selected position");
      connections.respondToSender(authToken,error);
      return;
    }
  }

  private void checkBlack(GameData game,String authToken,ChessMove chessMove,Integer gameID) throws Exception{
    if (game.game().getTeamTurn().equals(ChessGame.TeamColor.WHITE)){
      Error error = new Error("Error: it's not your turn");
      connections.respondToSender(authToken,error);
      return;
    }
    else{
      if (game.game().getBoard().pieceAtPosition(chessMove.getStartPosition())){
        var piece = game.game().getBoard().getPiece(chessMove.getStartPosition());
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
          Error error = new Error("Error: not your teams piece");
          connections.respondToSender(authToken,error);
          return;
        }
        var moves = game.game().validMoves(chessMove.getStartPosition());
        if(moves.contains(chessMove)){
          game.game().makeMove(chessMove);
          LoadGame loadGame = new LoadGame(game);
          GameService.gameAccess.updateGame(gameID,game);
          connections.respondToSender(authToken,loadGame);
          connections.broadcast(authToken,loadGame);
          if (game.game().isInCheck(ChessGame.TeamColor.WHITE)){
            String message = String.format("%s is in check",game.whiteUsername());
            Notification notification = new Notification(message);
            connections.broadcast(authToken,notification);
            connections.respondToSender(authToken,notification);
          }
          else if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE)){
            String message = String.format("%s is in checkmate",game.whiteUsername());
            isGameOver(game,authToken,gameID,message);
          }
          else if (game.game().isInStalemate(ChessGame.TeamColor.WHITE)){
            String message = String.format("%s is in stalemate",game.whiteUsername());
            isGameOver(game,authToken,gameID,message);
          }
          else{
            String message = String.format("%s moved %s",game.blackUsername(),chessMove.toString());
            createNotification(authToken,message);
          }
          return;
        }
        else{
          Error error = new Error("Error: not a valid move");
          connections.respondToSender(authToken,error);
          return;
        }
      }
      Error error = new Error("Error: no piece at selected position");
      connections.respondToSender(authToken,error);
      return;
    }
  }

  private void isGameOver(GameData game,String authToken, Integer gameID,String message) throws Exception{
    var updatedGame = new GameData(game.gameID(),null,null,null,null);
    GameService.gameAccess.updateGame(gameID,updatedGame);
    Notification notification = new Notification(message);
    connections.broadcast(authToken,notification);
    connections.respondToSender(authToken,notification);
    connections.remove(authToken);
    connManager.put(gameID,connections);
  }

  private void createNotification(String authToken,String message) throws Exception{
    Notification notification = new Notification(message);
    connections.broadcast(authToken,notification);
  }
}