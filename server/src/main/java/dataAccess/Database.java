package dataAccess;

import java.sql.SQLException;

public class Database implements GameDAO,UserDAO,AuthDAO{
  @Override
  public int sizeOfAuthTokens() {
    return 0;
  }

  @Override
  public int lengthOfGames() {
    return 0;
  }

  @Override
  public Integer lengthOfUsers() {
    return null;
  }



 public void configureDatabase(String[] createStatements) throws DataAccessException, SQLException {
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()){
      for (var statement : createStatements){
        try (var preparedStatement = conn.prepareStatement(statement)){
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e);
    }
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
}
