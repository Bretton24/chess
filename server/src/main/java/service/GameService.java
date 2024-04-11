package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;

import java.util.ArrayList;

public class GameService extends Services {


  public GameID createGame(String authToken, GameName name) throws Exception {
    if (!authAccess.authTokenPresent(authToken)){
      throw new UnauthorizedAccessException("Error: unathorized game creation");
    }
    return gameAccess.createGame(name);
  }

  public ArrayList<GameData> listGames(String authToken) throws Exception {
    if (!authAccess.authTokenPresent(authToken)){
      throw new UnauthorizedAccessException("Error: unauthorized token");
    }
    return gameAccess.listGamesArray();
  }

  public void updateGame(int gameID, ChessGame game) throws Exception {
    gameAccess.updateGame(gameID,game);
  }

  public Integer returnGamesLength() throws DataAccessException {
    return gameAccess.lengthOfGames();
  }
  public GameData joinGame(String authToken,PlayerInfo playerInfo) throws Exception {
    if(!authAccess.authTokenPresent(authToken)){
      throw new UnauthorizedAccessException("Error: unathorized token");
    }
    var user = authAccess.getUser(authToken);
    gameAccess.joinGame(playerInfo,user);
    return gameAccess.getGame(playerInfo.gameID());
  }

}
