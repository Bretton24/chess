package service;

import dataAccess.*;

public class Services {

  public static AuthDAO authAccess = new MemoryAuthDAO();
  public static GameDAO gameAccess = new MemoryGameDAO();
  public static UserDAO userAccess = new MemoryUserDAO();

  public static void specifySQLOrMemory(boolean useSQL) {
    if (useSQL) {
      authAccess = new SQLAuthDAO();
      gameAccess = new SQLGameDAO();
      userAccess = new SQLUserDAO();
    }
  }


}
