package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{

  final private HashMap<String,UserData> users = new HashMap<>();

  public UserData createUser(UserData user) throws DuplicateException,BadRequestException{
    if (user.username() == null || user.password() == null || user.email() == null){
      throw new BadRequestException("Error: bad request");
    }
    if (!users.containsKey(user.username())){
      users.put(user.username(), user);
      return user;
    }
    else{
      throw new DuplicateException("Error: already taken");
    }
  }


  public UserData getUser(UserData user) throws BadRequestException,UnauthorizedAccessException {
    if (users.containsKey(user.username())){
      var existingUser = users.get(user.username());

      if (existingUser.password().equals(user.password())){
        return user;
      }
      else{
        throw new UnauthorizedAccessException("Error: incorrect password");
      }

    }
    else{
      throw new UnauthorizedAccessException("Error: user does not exist");
    }
  }

  public void deleteAllUsers() throws DataAccessException {
    users.clear();
    if (!users.isEmpty()){
      throw new DataAccessException("Error: users not deleted");
    }
  }

  public Integer lengthOfUsers(){
    return users.size();
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
}
