package service;

import dataAccess.*;

public class Services {
  public static GameDAO gameAccess = new MemoryGameDAO();
  public static UserDAO userAccess = new MemoryUserDAO();
  public static AuthDAO authAccess = new MemoryAuthDAO();

}
