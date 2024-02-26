package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MemoryUserDAO implements UserDAO{
  private int nextId = 1;
  final private HashSet<UserData> users = new HashSet<>();

  public UserData createUser(UserData user){
    if (!users.contains(user)){
      users.add(user);
      return user;
    }
    return user;
  }
  public boolean getUser(UserData user){
    return users.contains(user);
  }
  @Override
  public void deleteAllUsers() {
    users.clear();
  }
}
