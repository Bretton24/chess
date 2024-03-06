package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
  default AuthData createAuth(UserData user) throws DataAccessException, DuplicateException {return new AuthData("","");}
  default UserData getUser(String newAuthToken) throws UnauthorizedAccessException{return new UserData("","","");}
  default boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{return true;}
  default void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{}
  default void deleteAllAuthTokens() throws Exception {}

  int sizeOfAuthTokens();
}
