package dataAccess;

import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
  default void deleteAllUsers() throws DataAccessException {}
  default UserData createUser(UserData data) throws DataAccessException{return data;}
}
