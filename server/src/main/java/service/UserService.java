package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;

public class UserService extends Services{
  public AuthData addUser(UserData user) throws DataAccessException, DuplicateException , BadRequestException, UnauthorizedAccessException {
    user = userAccess.createUser(user);
    return authAccess.createAuth(user);
  }
  public AuthData loginUser(UserData user) throws DuplicateException,BadRequestException, UnauthorizedAccessException,DataAccessException {
    user = userAccess.getUser(user);
    return authAccess.createAuth(user);
  }

}
