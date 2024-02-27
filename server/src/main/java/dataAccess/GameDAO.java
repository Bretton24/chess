package dataAccess;

public interface GameDAO {
  default void deleteAllGames() throws DataAccessException{}
}
