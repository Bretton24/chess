package dataAccessTests;

import dataAccess.*;
import model.*;
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
    gameDAO.createGame(new GameName("steve"));
    gameDAO.deleteAllGames();
    assertEquals(0,gameDAO.lengthOfGames());
  }

  @Test
  public void testGetGames_Positive() throws Exception {
    gameDAO.createGame(new GameName("Stevie Wonder"));
    gameDAO.deleteAllGames();
    assertEquals(0,gameDAO.lengthOfGames());
  }

  @Test
  public void testGetGames_Negative() throws Exception {
      var id = gameDAO.createGame(new GameName("value"));
      gameDAO.deleteAllGames();
      assertEquals(0,gameDAO.lengthOfGames());
  }

  @Test
  public void testListGames_Positive() throws Exception {
    gameDAO.createGame(new GameName("value"));
    gameDAO.createGame(new GameName("steve"));
    gameDAO.createGame(new GameName("jobs"));
    assertInstanceOf(GameList.class,gameDAO.listGamesArray());
  }

  @Test
  public void testListGames_Negative () throws Exception {
    gameDAO.createGame(new GameName("value"));
    gameDAO.createGame(new GameName("steve"));
    gameDAO.createGame(new GameName("jobs"));
    gameDAO.deleteAllGames();
    assertThrows(DataAccessException.class,() -> gameDAO.listGamesArray());
  }

  @Test
  public void testJoinGame_Positive () throws Exception {
    SQLUserDAO userAccess = new SQLUserDAO();
    SQLAuthDAO authAccess = new SQLAuthDAO();
    var user = userAccess.createUser(new UserData("sup","joh","steve"));
    var authToken = authAccess.createAuth(user);
    gameDAO.createGame(GameName("gamename is here"));
    PlayerInfo player = new PlayerInfo("WHITE",)
    gameDAO.createGame(new GameName("steve"));


  }
}
