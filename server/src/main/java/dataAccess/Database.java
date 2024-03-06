package dataAccess;

import java.sql.SQLException;

import static java.sql.Types.NULL;

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

  public int executeUpdate(String statement, Object ... params) throws DataAccessException{
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
}
