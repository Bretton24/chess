package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;

public interface GameDAO {
  default void deleteAllGames() throws DataAccessException{}
  default GameID createGame(GameName gameName) throws DataAccessException {return new GameID(1);}
  default GameData getGame(int gameID){
    return new GameData(gameID,"","","", new ChessGame());
  }
  default void joinGame(PlayerInfo playerInfo, UserData user) throws DuplicateException, BadRequestException, DataAccessException{}
  default GameList listGamesArray(){return new GameList(new ArrayList<>());}

  int lengthOfGames();
}
