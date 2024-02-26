package dataAccess;

import passoffTests.testClasses.TestException;

public interface AuthDAO {
  default void deleteAllAuthTokens(){}
}
