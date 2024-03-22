package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

  public SQLGameDAO() throws Exception {
    configureDatabase();
  }
  @Override
  public void deleteAllGames() throws Exception {
    var statement = "TRUNCATE games";
    executeUpdate(statement);
  }
  @Override
  public GameID createGame(GameName gameName) throws Exception {
    var statement = "INSERT INTO games (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)";
    ChessGame chessGame = new ChessGame();
    var cGame = new Gson().toJson(chessGame);
    var id = executeUpdate(statement,null,null, gameName.gameName(),cGame);
    var gameid = new GameID(id);
    return gameid;
  }
  @Override
  public GameData getGame(int gameID) throws Exception {
      try (var conn = DatabaseManager.getConnection()) {
        var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,chessGame FROM games WHERE gameID=?";
        try (var ps = conn.prepareStatement(statement)) {
          ps.setInt(1, gameID);
          try (var rs = ps.executeQuery()) {
            if (rs.next()) {
               return readChessGame(rs);
            }
          }
        }
      } catch (Exception e) {
        throw new DataAccessException("Error: unable to get game");
      }
      return null;
    }

  @Override
  public void joinGame(PlayerInfo playerInfo, UserData user) throws Exception {
    var game = getGame(playerInfo.gameID());
    if (game == null){
      throw new BadRequestException("Error: game does not exist");
    }
    if (playerInfo.playerColor() != null){
      if (playerInfo.playerColor().equals("WHITE")){
        if (game.whiteUsername() == null){
          var statement = "UPDATE games SET whiteUsername = ? WHERE gameID = ?";
          executeUpdate(statement, user.username(), game.gameID());
        }
        else{
          throw new DuplicateException("Error: white team taken");
        }
      }
      else if (playerInfo.playerColor().equals("BLACK")){
        if (game.blackUsername() == null) {
          var statement = "UPDATE games SET blackUsername = ? WHERE gameID = ?";
          executeUpdate(statement, user.username(), game.gameID());
        }
        else{
          throw new DuplicateException("Error: black team taken");
        }
      }
      else{
        throw new DataAccessException("Error: unable to join game");
      }
    }

  }
  @Override
  public GameList listGamesArray() throws Exception {
    var listOfGames = new ArrayList<GameData>();
    try (var conn = DatabaseManager.getConnection()) {
      var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,chessGame FROM games";
      try (var ps = conn.prepareStatement(statement)) {
        try (var rs = ps.executeQuery()) {
          while (rs.next()) {
            listOfGames.add(readChessGame(rs));
          }
        }
      }
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
    return new GameList(listOfGames);
  }

  private GameData readChessGame(ResultSet rs) throws SQLException {
    var id = rs.getInt("gameID");
    var white = rs.getString("whiteUsername");
    var black = rs.getString("blackUsername");
    var gameName = rs.getString("gameName");
    var json = rs.getString("chessGame");
    var chessGame = new Gson().fromJson(json, ChessGame.class);
    return new GameData(id,white,black,gameName,chessGame);
  }

  @Override
  public int lengthOfGames() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement("SELECT COUNT(*) FROM games")) {
      try (var rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException | DataAccessException e) {
      throw new DataAccessException("Error: Unable to access database");
    }
    return 0; // If no data is retrieved, return 0
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
