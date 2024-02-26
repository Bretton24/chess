package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;


public class DeleteService {
  private final GameDAO gameAccess;
  private final UserDAO userAccess;
  private final AuthDAO authAccess;

  public DeleteService(GameDAO gameAccess,UserDAO userAccess,AuthDAO authAccess){
    this.gameAccess = gameAccess;
    this.userAccess = userAccess;
    this.authAccess = authAccess;
  }

  public void deleteDatabase(){
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
    authAccess.deleteAllAuthTokens();
  }
}
