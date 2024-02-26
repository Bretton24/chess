package dataAccess;

import model.AuthData;
import model.UserData;
import passoffTests.testClasses.TestException;

public interface AuthDAO {
  default void deleteAllAuthTokens(){}
  default AuthData createAuth(UserData user){return new AuthData("","");}
}
