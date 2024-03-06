package service;

import dataAccess.*;
import handlers.Logout;
import model.AuthData;
import model.UserData;

public class AuthService extends Services {

  public void logoutUser(String authToken) throws UnauthorizedAccessException , DataAccessException {
    authAccess.deleteAuth(authToken);
  }


  }