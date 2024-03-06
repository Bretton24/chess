package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

  public SQLGameDAO() throws SQLException, DataAccessException {
    configureDatabase();
  }
  @Override
  public void deleteAllGames() throws DataAccessException{
    var statement = "TRUNCATE games";
    executeUpdate(statement);
  }
  @Override
  public GameID createGame(GameName gameName) throws DataAccessException {
//    var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?, ?)";
//    ChessGame chessGame = new ChessGame();
//    var cGame = new Gson().toJson(chessGame);
//    var id = executeUpdate(statement,null,null, gameName.gameName(),cGame);
//    var gameid = new GameID(id);
//    return gameid;
    return new GameID(12);
  }
  @Override
  public GameData getGame(int gameID){
//    var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,chessGame FROM games WHERE gameID=?";
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
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL AUTO_INCREMENT ,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `chessGame` TEXT,
              PRIMARY KEY (`GameID`),
              INDEX(gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """

  };

  private void configureDatabase() throws Exception {
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = conn.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new Exception(ex);
    }
  }

  private int executeUpdate(String statement, Object... params) throws Exception {
    try (var conn = DatabaseManager.getConnection()) {
      try (var ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
        for (var i = 0; i < params.length; i++) {
          var param = params[i];
          if (param instanceof String p) ps.setString(i + 1, p);
          else if (param instanceof Integer p) ps.setInt(i + 1, p);
          else if (param == null) ps.setNull(i + 1, NULL);
        }
        ps.executeUpdate();

        var rs = ps.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1);
        }

        return 0;
      }
    } catch (SQLException e) {
      throw new Exception(e);
    }
  }


}
