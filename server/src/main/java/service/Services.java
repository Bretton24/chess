package service;

import dataAccess.*;

public class Services {
  public static MemoryGameDAO gameAccess = new MemoryGameDAO();
  public static MemoryUserDAO userAccess = new MemoryUserDAO();
  public static MemoryAuthDAO authAccess = new MemoryAuthDAO();


}
