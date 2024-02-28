package serviceTests;




import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
  static final UserService service = new UserService();

  @Test
  public void addUserSuccessTest() {
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    try {
      service.addUser(user);
      assertEquals(1, service.userAccess.lengthOfUsers());
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }
  }
  @Test
  public void addUserDuplicateTest(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    var other=new UserData("chad123", "hey123", "steve@gmail.com");
    try {
      service.addUser(user);
      assertThrows(DuplicateException.class,() -> service.addUser(other));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }
  }

  @Test
  public void loginUserTest(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      service.addUser(user);
      var authToken = service.loginUser(user);
      assertTrue(authAccess.authTokenPresent(authToken.authToken()));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }
  }

  @Test
  public void loginUserTestFail(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      service.addUser(user);
      var sameUser = new UserData(null,user.password(),user.email());
      assertThrows(UnauthorizedAccessException.class, () -> service.loginUser(sameUser));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }
  }
}
