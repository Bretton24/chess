package dataAccessTests;

import dataAccess.SQLAuthDAO;
import dataAccess.SQLUserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLUserDAOTests {

  SQLUserDAO userDAO = new SQLUserDAO();
  public SQLUserDAOTests() throws Exception {
  }

  @Test
  public void testDeleteUsers_Positive() throws Exception {
    userDAO.deleteAllUsers();
    assertEquals(0, userDAO.lengthOfUsers());
  }
}
