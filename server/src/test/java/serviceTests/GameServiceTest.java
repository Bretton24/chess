package serviceTests;

import dataAccess.*;
import model.*;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
  static final GameService service = new GameService();
  static final UserService helperService = new UserService();

  @Test
  public void createGameSuccess(){
      var user=new UserData("chad123", "hey123", "steve@gmail.com");
      MemoryAuthDAO authAccess = new MemoryAuthDAO();
      try {
        helperService.addUser(user);
        var authToken = helperService.loginUser(user);
        var gameName = new GameName("sup");
        service.createGame(authToken.authToken(),gameName);
        assertEquals(1,service.returnGamesLength());
      } catch (UnauthorizedAccessException e) {
        //nothing happens
      } catch (DuplicateException e) {
        //nothing happens
      } catch (BadRequestException e) {
        //nothing happens
      } catch (DataAccessException e) {
        //nothing happens
      }catch (Exception e){
        //nothing happens
      }
  }

  @Test
  public void createGameFailure(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      helperService.addUser(user);
      var authToken = helperService.loginUser(user);
      var gameName = new GameName("sup");
      AuthData newAuthToken = new AuthData("wrong","stevie wonder");
      service.createGame(authToken.authToken(),gameName);
      assertThrows(UnauthorizedAccessException.class, () -> service.createGame(newAuthToken.authToken(),gameName));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }catch (Exception e){
      //nothing happens
    }
  }

  @Test
  public void listGames(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      helperService.addUser(user);
      var authToken = helperService.loginUser(user);
      var gameName = new GameName("sup");
      service.createGame(authToken.authToken(),gameName);
      service.createGame(authToken.authToken(),new GameName("dude"));
      service.createGame(authToken.authToken(),new GameName("hey"));
      var list = service.listGames(authToken.authToken());
      assertEquals("sup",list.get(0).gameName());
      assertEquals("dude",list.get(1).gameName());
      assertEquals("hey",list.get(2).gameName());
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }catch (Exception e){
      //nothing happens
    }
  }

  @Test
  public void listGamesFailure(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    try {
      helperService.addUser(user);
      var authToken = helperService.loginUser(user);
      var gameName = new GameName("sup");
      service.createGame(authToken.authToken(),gameName);
      AuthData newAuthToken = new AuthData("wrong","stevie wonder");
      service.createGame(authToken.authToken(),gameName);
      assertThrows(UnauthorizedAccessException.class, () -> service.listGames(newAuthToken.authToken()));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }catch (Exception e){
      //nothing happens
    }
  }

  @Test
  public void joinGame(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    PlayerInfo player = new PlayerInfo("WHITE",1);
    try {
      helperService.addUser(user);
      var authToken = helperService.loginUser(user);
      var gameName = new GameName("sup");
      service.createGame(authToken.authToken(),gameName);
      service.joinGame(authToken.authToken(),player);
      var list = service.listGames(authToken.authToken());
      assertEquals("chad123",list.get(0).whiteUsername());
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
  public void joinGameFailure(){
    var user=new UserData("chad123", "hey123", "steve@gmail.com");
    MemoryAuthDAO authAccess = new MemoryAuthDAO();
    PlayerInfo player = new PlayerInfo("GREEN",1);
    try {
      helperService.addUser(user);
      var authToken = helperService.loginUser(user);
      var gameName = new GameName("sup");
      service.createGame(authToken.authToken(),gameName);
      assertThrows(DataAccessException.class,() -> service.joinGame(authToken.authToken(),player));
    } catch (UnauthorizedAccessException e) {
      //nothing happens
    } catch (DuplicateException e) {
      //nothing happens
    } catch (BadRequestException e) {
      //nothing happens
    } catch (DataAccessException e) {
      //nothing happens
    }catch (Exception e){
      //nothing happens
    }
  }


}


