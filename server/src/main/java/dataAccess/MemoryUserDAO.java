package dataAccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{
  private int nextId = 1;
  final private HashMap<Integer, UserData> users = new HashMap<>();
  @Override
  public void deleteAllUsers() {
    users.clear();
  }
}
