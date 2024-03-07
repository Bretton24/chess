package dataAccessTests;

import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLGameDAOTests {
  SQLGameDAO gameDAO = new SQLGameDAO();
  public SQLGameDAOTests() throws Exception {
  }

  @Test
  public void testDeleteGames_Positive() throws Exception {
    gameDAO.deleteAllGames();
    assertEquals(0, gameDAO.lengthOfGames());
  }
}
