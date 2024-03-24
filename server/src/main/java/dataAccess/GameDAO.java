package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;

public interface GameDAO {
  default void deleteAllGames() throws Exception {}
  default GameID createGame(GameName gameName) throws Exception {return new GameID(1);}
  default GameData getGame(int gameID) throws Exception {
    return new GameData(gameID,"","","", new ChessGame());
  }
  default void joinGame(PlayerInfo playerInfo, UserData user) throws Exception {}
  default ArrayList<GameData> listGamesArray() throws Exception {return new ArrayList<>();}

  int lengthOfGames() throws DataAccessException;
}
