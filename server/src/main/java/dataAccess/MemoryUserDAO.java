package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemoryUserDAO that=(MemoryUserDAO) o;
    return nextId == that.nextId && Objects.equals(users, that.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nextId, users);
  }

  public boolean getUser(UserData user){
    return users.contains(user);
  }
  @Override
  public void deleteAllUsers() {
    users.clear();
  }
}
