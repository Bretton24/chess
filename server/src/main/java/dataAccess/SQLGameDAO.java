package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;

public class SQLGameDAO implements GameDAO{
  @Override
  public void deleteAllGames() throws DataAccessException{}
  @Override
  public GameID createGame(GameName gameName){return new GameID(1);}
  @Override
  public GameData getGame(int gameID){
    return new GameData(gameID,"","","", new ChessGame());
  }
  @Override
  public void joinGame(PlayerInfo playerInfo, UserData user) throws DuplicateException, BadRequestException, DataAccessException{}
  @Override
  public GameList listGamesArray(){return new GameList(new ArrayList<>());}
}
