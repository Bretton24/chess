package dataAccess;

import model.UserData;

import java.sql.SQLException;

import static java.sql.Types.NULL;

public class SQLUserDAO extends Database{

  @Override
  public void deleteAllUsers() throws DataAccessException {}
  @Override
  public UserData createUser(UserData data)throws DuplicateException,BadRequestException {return data;}
  @Override
  public UserData getUser(UserData user) throws BadRequestException,UnauthorizedAccessException{return user;}

  @Override
  public Integer lengthOfUsers() {
    return null;
  }


  private int executeUpdate(String statement, Object ... params) throws DataAccessException{
    try (var conn = DatabaseManager.getConnection()){
      try (var ps = conn.prepareStatement(statement)){
        for (var i = 0; i < params.length; i++){
          var param = params[i];
          if (param instanceof Integer p) ps.setInt(i + 1, p);
          else if (param instanceof String p) ps.setString(i + 1, p);
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
