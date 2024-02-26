package service;

import dataAccess.*;


public class DeleteService extends Services {
  public void deleteDatabase(){
    gameAccess.deleteAllGames();
    userAccess.deleteAllUsers();
    authAccess.deleteAllAuthTokens();
  }
}
