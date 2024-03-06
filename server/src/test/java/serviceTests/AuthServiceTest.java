package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
  static final AuthService service = new AuthService();
  static final UserService useService = new UserService();

  @Test
  public void logoutUser(){
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    try {
      useService.addUser(user);
      var authToken = useService.loginUser(user);
      service.logoutUser(authToken.authToken());
      assertThrows(UnauthorizedAccessException.class, () -> authAccess.authTokenPresent(authToken.authToken()));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    } catch (Exception e){
      //nothing happens
    }
  }

  @Test
  public void failedLogout(){
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      AuthData authToken = new AuthData("this is fake","steve");
      service.logoutUser(authToken.authToken());
      assertThrows(UnauthorizedAccessException.class, () -> authAccess.deleteAuth(authToken.authToken()));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    }  catch (DataAccessException e) {
      //nothing happens
    }
  }
}
