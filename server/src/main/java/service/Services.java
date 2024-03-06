package service;

import dataAccess.*;

public class Services {

  public static AuthDAO authAccess = new SQLAuthDAO();
  public static GameDAO gameAccess = new SQLGameDAO();
  public static UserDAO userAccess = new SQLUserDAO();

//  public static void specifySQL() {
//      authAccess = new SQLAuthDAO();
//      gameAccess = new SQLGameDAO();
//      userAccess = new SQLUserDAO();
//  }
}
