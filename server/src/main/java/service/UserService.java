package service;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import model.AuthData;
import model.UserData;

public class UserService extends Services{


  public AuthData addUser(UserData user) throws DataAccessException, DuplicateException , BadRequestException {
    user = userAccess.createUser(user);
    return authAccess.createAuth(user);
  }
  public AuthData loginUser(UserData user) throws DataAccessException {
    user = userAccess.getUser(user);
    return authAccess.createAuth(user);
  }

}
