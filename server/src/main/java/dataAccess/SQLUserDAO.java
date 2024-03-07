package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Types.NULL;

public class SQLUserDAO implements UserDAO{

  public SQLUserDAO() throws SQLException, DataAccessException,Exception {
    configureDatabase();
  }
  @Override
  public void deleteAllUsers() throws Exception {
    var statement = "TRUNCATE users";
    executeUpdate(statement);
  }
  @Override
  public UserData createUser(UserData user) throws Exception {
    if (user.username() == null || user.password() == null || user.email() == null){
      throw new BadRequestException("Error: bad request");
    }
    // Check if the username already exists
    if (usernameExists(user.username())) {
      throw new DuplicateException("Error: username already exists");
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPassword = encoder.encode(user.password());
    var statement = "INSERT INTO users (userName, userPassword, email) VALUES (?, ?, ?)";
    var id = executeUpdate(statement, user.username(),hashedPassword,user.email());
    return user;
  }
  @Override
  public UserData getUser(UserData user) throws BadRequestException, UnauthorizedAccessException, DataAccessException, SQLException {
    try (var conn = DatabaseManager.getConnection()) {
      var statement = "SELECT userPassword FROM users WHERE userName=?";
      try (var ps = conn.prepareStatement(statement)) {
        ps.setString(1, user.username());
        try (var rs = ps.executeQuery()) {
          if (rs.next()) {
            String hashedPasswordFromDatabase = rs.getString("userPassword");
            if (BCrypt.checkpw(user.password(), hashedPasswordFromDatabase)) {
              return user;
            } else {
              throw new UnauthorizedAccessException("Error: Password does not match");
            }
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
  public Integer lengthOfUsers() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement("SELECT COUNT(*) FROM users")) {
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

  // Method to check if a username already exists in the database
  private boolean usernameExists(String username) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE userName = ?")) {
      ps.setString(1, username);
      try (var rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: checking username existence");
    }
    return false;
  }


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
  public final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  users (
              `userID` int NOT NULL AUTO_INCREMENT ,
              `userName` varchar(256),
              `userPassword` varchar(256),
              `email` varchar(256),
              PRIMARY KEY (`userID`),
              INDEX(userID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
  };

}
