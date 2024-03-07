package serviceTests;

import dataAccess.*;
import org.junit.jupiter.api.Test;
import service.DeleteService;
import service.Services;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteServiceTest {
  static final DeleteService service = new DeleteService();



  @Test
  public void deleteAll() {
   try{
     service.deleteDatabase();
     assertEquals(0,service.gameAccess.lengthOfGames());
     assertEquals(0,service.authAccess.sizeOfAuthTokens());
     assertEquals(0,service.userAccess.lengthOfUsers());
   }
   catch(DataAccessException e){
     e.getMessage();
   } catch (Exception e) {
     throw new RuntimeException(e);
   }


  }
}
