package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;


public class DeleteService extends Services {

  public void deleteDatabase() throws DataAccessException {
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
    authAccess.deleteAllAuthTokens();
  }
}
