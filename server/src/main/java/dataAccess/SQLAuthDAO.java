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
    buildDatabase();
  }
  @Override
  public AuthData createAuth(UserData user) throws Exception {
    var statement = "INSERT INTO authTokens (authToken, userName,user) VALUES (?, ?, ?)";
    var authToken = new AuthData(UUID.randomUUID().toString(), user.username());
    var newUser = new Gson().toJson(user);
    if(!authTokenPresent(authToken.authToken())){
      int id = doUpdate(statement, authToken.authToken(),authToken.username(),newUser);
      return authToken;
    }
    else{
      throw new DataAccessException("Error: authToken already exists");
    }
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
      doUpdate(statement, newAuthToken);
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
    doUpdate(statement);
  }

  @Override
  public int sizeOfAuthTokens() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement("SELECT COUNT(*) FROM authTokens")) {
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


  private void buildDatabase() throws Exception {
    DatabaseManager.createDatabase();
    try (var connection = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = connection.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new Exception(ex);
    }
  }

  private int doUpdate(String phrase, Object... params) throws Exception {
    try (var connectivity = DatabaseManager.getConnection()) {
      try (var prep = connectivity.prepareStatement(phrase, Statement.RETURN_GENERATED_KEYS)) {
        for (var iter = 0; iter < params.length; iter++) {
          var param = params[iter];
          if (param instanceof String p) prep.setString(iter + 1, p);
          else if (param instanceof Integer p) prep.setInt(iter + 1, p);
          else if (param == null) prep.setNull(iter + 1, NULL);
        }
        prep.executeUpdate();

        var rs = prep.getGeneratedKeys();
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
