package DataAccessTests;

import dataAccess.SQLAuthDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SQLAuthDAOTest {

  SQLAuthDAO authDAO = new SQLAuthDAO();

  public SQLAuthDAOTest() throws Exception {
  }

  @Test
    public void testCreateAuth_Positive() throws Exception {
      // Create a mock user
      UserData user = new UserData("testUser", "testPassword", "test@example.com");

      // Create a mock SQLAuthDAO object


      try {
        // Call the createAuth method
        AuthData authData = authDAO.createAuth(user);

        // Check that an AuthData object is returned
        Assertions.assertNotNull(authData);
        // Check that the authToken is not null or empty
        Assertions.assertNotNull(authData.authToken());
        Assertions.assertFalse(authData.authToken().isEmpty());
        // Check that the username matches the provided user's username
        Assertions.assertEquals(user.username(), authData.username());
      } catch (Exception e) {
        // If an exception is thrown, fail the test
        Assertions.fail("Exception thrown: " + e.getMessage());
      }
    }



  @Test
  public void testDeleteAllAuthTokens_Positive() {

    try {
      // Call the deleteAllAuthTokens method
      authDAO.deleteAllAuthTokens();

      // No exception is thrown, meaning the test passes
    } catch (Exception e) {
      // If an exception is thrown, fail the test
      Assertions.fail("Exception thrown: " + e.getMessage());
    }
  }
  }

