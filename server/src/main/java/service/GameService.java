package service;

import dataAccess.*;
import model.*;

import java.util.ArrayList;

public class GameService extends Services {

  public GameID createGame(String authToken, GameName name) throws UnauthorizedAccessException {
    if (!authAccess.authTokenPresent(authToken)){
    }
    return gameAccess.createGame(name);
  }

  public GameList listGames(String authToken) throws UnauthorizedAccessException {
    if (!authAccess.authTokenPresent(authToken)){
    }
    return gameAccess.listGamesArray();
  }
}
