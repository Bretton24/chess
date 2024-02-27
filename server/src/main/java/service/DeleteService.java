package service;

import dataAccess.DataAccessException;


public class DeleteService extends Services {
  public void deleteDatabase() throws DataAccessException {
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
    authAccess.deleteAllAuthTokens();
  }
}
