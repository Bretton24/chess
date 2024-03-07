package dataAccessTests;

import dataAccess.*;
import model.GameID;
import model.GameName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLGameDAOTests {
  SQLGameDAO gameDAO = new SQLGameDAO();
  public SQLGameDAOTests() throws Exception {
  }

  @Test
  public void testDeleteGames_Positive() throws Exception {
    gameDAO.deleteAllGames();
    assertEquals(0, gameDAO.lengthOfGames());
  }


  @Test
  public void testCreateGames_Positive() throws Exception {
    var game = gameDAO.createGame(new GameName("steve johnson"));
    var newGame = gameDAO.getGame(game.gameID());
    var gameID = new GameID(newGame.gameID());
    assertTrue(game.gameID().equals(gameID.gameID()));
  }
  @Test
  public void testCreateGames_Negative() throws Exception {
    assertThrows(DataAccessException.class,() -> gameDAO.getGame(123));
  }

  @Test
  public void testGetGames_Positive() throws Exception {
    gameDAO.createGame(new GameName("Stevie Wonder"));
    gameDAO.deleteAllGames();
    assertEquals(0,gameDAO.lengthOfGames());
  }

  @Test
  public void testGetGames_Negative() throws Exception {

  }
}
