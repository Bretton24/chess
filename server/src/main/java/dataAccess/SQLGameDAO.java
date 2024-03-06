package dataAccess;

import chess.ChessGame;
import model.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{
  @Override
  public void deleteAllGames() throws DataAccessException{}
  @Override
  public GameID createGame(GameName gameName){return new GameID(1);}
  @Override
  public GameData getGame(int gameID){
    return new GameData(gameID,"","","", new ChessGame());
  }
  @Override
  public void joinGame(PlayerInfo playerInfo, UserData user) throws DuplicateException, BadRequestException, DataAccessException{}
  @Override
  public GameList listGamesArray(){return new GameList(new ArrayList<>());}


  private int executeUpdate(String statement, Object ... params) throws DataAccessException{
    try (var conn = DatabaseManager.getConnection()){
      try (var ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
        for (var i = 0; i < params.length; i++){
          var param = params[i];
          if (param instanceof String p) ps.setString(i + 1, p);
          else if (param instanceof Integer p) ps.setInt(i + 1, p);
          else if (param instanceof GameData p) ps.setString(i + 1, p.toString());
          else if (param == null) ps.setNull(i + 1, NULL);
        }
        ps.executeUpdate();

        var rs = ps.getGeneratedKeys();
        if (rs.next()){
          return rs.getInt(1);
        }

        return 0;
      }
    }catch(DataAccessException | SQLException e){
      throw new DataAccessException("unable to update database");
    }
  }
  private final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  Games (
              `gameID` int NOT NULL ,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `chessGame` TEXT DEFAULT NULL,
              PRIMARY KEY (`GameID`),
              INDEX(gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
  };

  private void configureDatabase() throws DataAccessException{
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()){
      for (var statement : createStatements){
        try (var preparedStatement = conn.prepareStatement(statement)){
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
