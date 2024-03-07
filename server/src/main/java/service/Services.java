package service;

import dataAccess.*;

import java.sql.SQLException;

public class Services {

  public static AuthDAO authAccess;

  static {
    try {
      authAccess=new SQLAuthDAO();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static GameDAO gameAccess;

  static {
    try {
      gameAccess=new SQLGameDAO();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static UserDAO userAccess;

  static {
    try {
      userAccess=new SQLUserDAO();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

//  public static void specifySQL() {
//      authAccess = new SQLAuthDAO();
//      gameAccess = new SQLGameDAO();
//      userAccess = new SQLUserDAO();
//  }
}
