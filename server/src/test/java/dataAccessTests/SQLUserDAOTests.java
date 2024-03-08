package dataAccessTests;

import dataAccess.BadRequestException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLUserDAO;
import dataAccess.UnauthorizedAccessException;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLUserDAOTests {

  SQLUserDAO userDAO = new SQLUserDAO();
  public SQLUserDAOTests() throws Exception {
  }

  @Test
  public void testDeleteUsers_Positive() throws Exception {
    userDAO.deleteAllUsers();
    assertEquals(0, userDAO.lengthOfUsers());
  }

  @Test
  public void createUser_Positive() throws Exception {
    userDAO.deleteAllUsers();
    var user = new UserData("Steve","Jobs","byu@email.com");
    userDAO.createUser(user);
    assertEquals(1,userDAO.lengthOfUsers());
  }

  @Test
  public void createUser_Negative() throws Exception {
    userDAO.deleteAllUsers();
    var user = new UserData(null,"Jobs","byu@email.com");
    assertThrows(BadRequestException.class,() -> userDAO.createUser(user));
  }

  @Test
  public void getUser_Positive() throws Exception {
    userDAO.deleteAllUsers();
    var user = new UserData("Steve","Jobs","byu@email.com");
    userDAO.createUser(user);
    var newUser = userDAO.getUser(user);
    assertTrue(newUser.equals(user));
  }

  @Test
  void getUser_Negative() throws Exception {
    userDAO.deleteAllUsers();
    var user = new UserData("steve","jobs","byu");
    assertThrows(UnauthorizedAccessException.class,()-> userDAO.getUser(user));
  }

}
