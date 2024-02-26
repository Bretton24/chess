package dataAccess;

import model.AuthData;
import model.UserData;

public interface UserDAO {
  default void deleteAllUsers(){}
  default UserData createUser(UserData data){return data;}
}
