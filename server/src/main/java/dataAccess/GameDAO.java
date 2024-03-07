package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;

public interface GameDAO {
  default void deleteAllGames() throws Exception {}
  default GameID createGame(GameName gameName) throws Exception {return new GameID(1);}
  default GameData getGame(int gameID){
    return new GameData(gameID,"","","", new ChessGame());
  }
  default void joinGame(PlayerInfo playerInfo, UserData user) throws DuplicateException, BadRequestException, DataAccessException{}
  default GameList listGamesArray() throws Exception {return new GameList(new ArrayList<>());}

  int lengthOfGames();
}
