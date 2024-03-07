package dataAccess;

import model.UserData;

import java.sql.SQLException;

public interface UserDAO {
  default void deleteAllUsers() throws Exception {}
  default UserData createUser(UserData data) throws Exception {return data;}
  default UserData getUser(UserData user) throws BadRequestException, UnauthorizedAccessException, DataAccessException, SQLException {return user;}

  Integer lengthOfUsers();
}
