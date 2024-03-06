package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class SQLGameDAO extends Database{
  @Override
  public void deleteAllGames() throws DataAccessException{
    var statement = "TRUNCATE games";
    executeUpdate(statement);
  }
  @Override
  public GameID createGame(GameName gameName) throws DataAccessException {
    var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?, ?)";
    ChessGame chessGame = new ChessGame();
    var cGame = new Gson().toJson(chessGame);
    var id = executeUpdate(statement,null,null, gameName.gameName(),cGame);
    var gameid = new GameID(id);
    return gameid;
  }
  @Override
  public GameData getGame(int gameID){
    var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,chessGame FROM games WHERE gameID=?";
    return new GameData(gameID,"","","", new ChessGame());
  }
  @Override
  public void joinGame(PlayerInfo playerInfo, UserData user) throws DuplicateException, BadRequestException, DataAccessException{}
  @Override
  public GameList listGamesArray(){return new GameList(new ArrayList<>());}

  @Override
  public int lengthOfGames() {
    return 0;
  }


public final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  Games (
              `gameID` int NOT NULL AUTO_INCREMENT ,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `chessGame` TEXT DEFAULT NOT NULL,
              PRIMARY KEY (`GameID`),
              INDEX(gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """

  };

  public void databaseConfig() throws SQLException, DataAccessException {
    configureDatabase(createStatements);
  }

}
