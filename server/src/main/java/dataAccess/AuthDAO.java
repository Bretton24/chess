package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
  default AuthData createAuth(UserData user) throws DataAccessException {return new AuthData("","");}
  default UserData getUser(String newAuthToken) throws UnauthorizedAccessException{return new UserData("","","");}
  default boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{return true;}
  default void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{}
  default void deleteAllAuthTokens() throws DataAccessException {}

  int sizeOfAuthTokens();
}
