package service;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import javax.xml.crypto.Data;
import java.util.UUID;

public class UserService extends Services{


  public AuthData addUser(UserData user) throws DataAccessException {
    user = userAccess.createUser(user);
    return authAccess.createAuth(user);
  }
  public AuthData loginUser(UserData user) throws DataAccessException {
    user = userAccess.getUser(user);
    return authAccess.createAuth(user);
  }

}
