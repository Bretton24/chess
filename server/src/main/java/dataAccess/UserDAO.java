package dataAccess;

import model.UserData;

public interface UserDAO {
  default void deleteAllUsers() throws DataAccessException {}
  default UserData createUser(UserData data)throws Exception {return data;}
  default UserData getUser(UserData user) throws BadRequestException,UnauthorizedAccessException{return user;}
}
