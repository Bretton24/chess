package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.AuthData;

public class GameService {
  private final GameDAO gameAccess;
  public GameService(GameDAO gamesAccess){
    this.gameAccess = gamesAccess;
  }
  public void clearGames(){
    gameAccess.deleteAllGames();
  }

}
