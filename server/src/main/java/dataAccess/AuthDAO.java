package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
  default AuthData createAuth(UserData user) throws Exception {return new AuthData("","");}
  default UserData getUser(String newAuthToken) throws UnauthorizedAccessException, DataAccessException {return new UserData("","","");}
  default boolean authTokenPresent(String newAuthToken) throws UnauthorizedAccessException, DataAccessException {return true;}
  default void deleteAuth(String newAuthToken) throws Exception {}
  default void deleteAllAuthTokens() throws Exception {}

  int sizeOfAuthTokens() throws DataAccessException;
}
