package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.AuthData;

public class GameService {
  public void clearAll(AuthDAO authAccess,GameDAO gameAccess,UserDAO userAccess){
    authAccess.deleteAllAuthTokens();
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
  }
}
