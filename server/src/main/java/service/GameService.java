package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.GameID;
import model.GameName;

public class GameService extends Services {

  public GameID createGame(String authToken, GameName name) throws UnauthorizedAccessException {
    if (!authAccess.authTokenPresent(authToken)){
    }
    return gameAccess.createGame(name);
  }
}
