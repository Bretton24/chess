package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import dataAccess.SQLAuthDAO;
import dataAccess.UnauthorizedAccessException;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLAuthDAOTests {
  SQLAuthDAO authDAO = new SQLAuthDAO();

  public SQLAuthDAOTests() throws Exception {
  }

  @Test
  public void testDeleteAuthTokens_Positive() throws Exception {
    authDAO.deleteAllAuthTokens();
    assertEquals(0, authDAO.sizeOfAuthTokens());
  }
  @Test
  public void createAuth_Positive() throws Exception {
    var user = new UserData("Steve","Jobs","byu@email.com");
    var authToken = authDAO.createAuth(user);
    assertTrue(authDAO.authTokenPresent(authToken.authToken()));
  }

  @Test
  public void createAuth_Negative() throws Exception {
    var user = new UserData("","Jobs","byu@email.com");
    var authToken = authDAO.createAuth(user);
    authDAO.deleteAllAuthTokens();
    assertFalse(authDAO.authTokenPresent(authToken.authToken()));
  }

  @Test
  public void getUserTest_Positive() throws Exception {
    var user = new UserData("","Jobs","byu@email.com");
    var authToken = authDAO.createAuth(user);
    assertTrue(user.equals(authDAO.getUser(authToken.authToken())));
  }

  @Test
  public void getUserTest_Negative() throws Exception {
    var authToken = "authDAO.createAuth(user)";
    assertThrows(UnauthorizedAccessException.class, () -> authDAO.getUser(authToken));
  }

  @Test
  public void deleteAuthToken_Positive() throws Exception{
    var user = new UserData("","Jobs","byu@email.com");
    var authToken = authDAO.createAuth(user);
    authDAO.deleteAuth(authToken.authToken());
    assertTrue(!authDAO.authTokenPresent(authToken.authToken()));
  }

  @Test
  public void deleteAuthToken_Negative() throws Exception{
    var authToken = "authDAO.createAuth(user";
    assertThrows(UnauthorizedAccessException.class, () -> authDAO.deleteAuth(authToken));
  }

}
