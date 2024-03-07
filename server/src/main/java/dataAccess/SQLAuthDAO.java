package dataAccess;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO{

  public SQLAuthDAO() throws Exception {
    configureDatabase();
  }
  @Override
  public AuthData createAuth(UserData user) throws Exception {
    var statement = "INSERT INTO authTokens (authToken, userName,user) VALUES (?, ?, ?)";
    var authToken = new AuthData(UUID.randomUUID().toString(), user.username());
    var newUser = new Gson().toJson(user);
    int id = executeUpdate(statement, authToken.authToken(),authToken.username(),newUser);
    return authToken;
  }

  @Override
  public UserData getUser(String newAuthToken) throws UnauthorizedAccessException, DataAccessException {
      try (var conn = DatabaseManager.getConnection()) {
        var statement = "SELECT user FROM authTokens WHERE authToken=?";
        try (var ps = conn.prepareStatement(statement)) {
          ps.setString(1, newAuthToken);
          try (var rs = ps.executeQuery()) {
            if (rs.next()) {
              String user = rs.getString("user");
              var newUser =  new Gson().fromJson(user,UserData.class);
              return newUser;
            } else {
              throw new UnauthorizedAccessException("Error: User does not exist");
            }
          }
        }
      } catch (SQLException e) {
        throw new DataAccessException("Error: Unable to access database");
      }
    }
  @Override
  public void deleteAuth(String newAuthToken) throws Exception {
    if (authTokenPresent(newAuthToken)){
      var statement = "DELETE FROM authTokens WHERE authToken=?";
      executeUpdate(statement, newAuthToken);
      if (authTokenPresent(newAuthToken)){
        throw new DataAccessException("Error: authtoken should've been removed");
      }
    }
    else{
      throw new UnauthorizedAccessException("Error: authtoken doesn't exist");
    }

  }
  @Override
  public void deleteAllAuthTokens() throws Exception {
    var statement = "TRUNCATE authTokens";
    executeUpdate(statement);
  }

  @Override
  public int sizeOfAuthTokens() {
    return 0;
  }


  public final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  authTokens (
              `authID` int NOT NULL AUTO_INCREMENT ,
              `authToken` varchar(256),
              `userName` varchar(256),
              `user` Text,
              PRIMARY KEY (`authID`),
              INDEX(authID)
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

  @Override
  public boolean authTokenPresent(String authToken) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement("SELECT COUNT(*) FROM authTokens WHERE authToken = ?")) {
      ps.setString(1, authToken);
      try (var rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (SQLException | DataAccessException e) {
      throw new DataAccessException("sql exception");
    }
    return false;
  }

}
