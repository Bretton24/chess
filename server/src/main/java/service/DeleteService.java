package service;

import dataAccess.*;


public class DeleteService extends Services {
  public void deleteDatabase() throws DataAccessException{
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
    authAccess.deleteAllAuthTokens();
  }
}
