package service;

import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class UserService extends Services{
  private final UserDAO userAccess;

  public UserService(UserDAO userAccess){
    this.userAccess = userAccess;
  }



}
