package dataAccess;

import model.UserData;

import java.util.HashSet;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{

  final private HashSet<UserData> users = new HashSet<>();

  public UserData createUser(UserData user) throws DuplicateException,BadRequestException{
    if (user.username() == null || user.password() == null){
      throw new BadRequestException("error: bad request");
    }
    if (!users.contains(user)){
      users.add(user);
      return user;
    }
    else{
      throw new DuplicateException("Error: already taken");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemoryUserDAO that=(MemoryUserDAO) o;
    return Objects.equals(users, that.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash( users);
  }

  public UserData getUser(UserData user) throws DataAccessException {
    if (users.contains(user)){
      return user;
    }
    else{
      throw new DataAccessException("User does not exist");
    }
  }

  public void deleteAllUsers() throws DataAccessException {
    users.clear();
    if (!users.isEmpty()){
      throw new DataAccessException("user still in users");
    }
  }
}
