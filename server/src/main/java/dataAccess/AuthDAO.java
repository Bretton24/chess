package dataAccess;

import model.AuthData;
import model.UserData;
import passoffTests.testClasses.TestException;

import javax.xml.crypto.Data;

public interface AuthDAO {
  default void deleteAllAuthTokens() throws DataAccessException {}
  default AuthData createAuth(UserData user) throws DataAccessException {return new AuthData("","");}
}
