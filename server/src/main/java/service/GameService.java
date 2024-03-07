package service;

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

  public GameList listGames(String authToken) throws Exception {
    if (!authAccess.authTokenPresent(authToken)){
      throw new UnauthorizedAccessException("Error: unathorized game creation");
    }
    return gameAccess.listGamesArray();
  }

  public Integer returnGamesLength(){
    return gameAccess.lengthOfGames();
  }
  public void joinGame(String authToken,PlayerInfo playerInfo) throws UnauthorizedAccessException,DuplicateException,BadRequestException,DataAccessException{
    if(!authAccess.authTokenPresent(authToken)){
    }
    var user = authAccess.getUser(authToken);
    gameAccess.joinGame(playerInfo,user);
  }

}
