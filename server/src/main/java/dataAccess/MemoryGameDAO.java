package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.GameID;
import model.GameName;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
  private int nextId = 1;
  final private HashMap<Integer,GameData> games = new HashMap<>();

  public GameID createGame(GameName gameName){
    ChessGame chessGame = new ChessGame();
    GameData game = new GameData(nextId++,null,null,gameName.gameName(), chessGame);
    games.put(game.gameID(),game);
    var gameid = new GameID(game.gameID());
    return gameid;
  }
  public GameData getGame(int gameID){
    return games.get(gameID);
  }
  @Override
  public void deleteAllGames() throws DataAccessException {
    games.clear();
    if (!games.isEmpty()){
      throw new DataAccessException("Error: games not deleted");
    }
  }

  public Integer lengthOfGames(){
    return games.size();
  }
}
