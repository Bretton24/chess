package service;

import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.UUID;

public class UserService extends Services{


  public AuthData addUser(UserData user){
    user = userAccess.createUser(user);
    return authAccess.createAuth(user);
  }
  public AuthData loginUser(UserData user){
    if (userAccess.getUser(user)){
      return authAccess.createAuth(user);
    }
    else{
      return null;
    }
  }

}
