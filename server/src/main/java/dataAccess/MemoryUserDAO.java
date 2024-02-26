package dataAccess;

import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryUserDAO implements UserDAO{
  private int nextId = 1;
  final private HashMap<UUID, UserData> users = new HashMap<>();

  public UserData createUser(UserData user){
    users.put(UUID.randomUUID(),user);
    return user;
  }
  @Override
  public void deleteAllUsers() {
    users.clear();
  }
}
