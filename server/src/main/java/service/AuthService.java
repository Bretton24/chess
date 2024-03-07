package service;

import dataAccess.*;
import handlers.Logout;
import model.AuthData;
import model.UserData;

public class AuthService extends Services {

  public void logoutUser(String authToken) throws Exception {
    authAccess.deleteAuth(authToken);
  }


  }