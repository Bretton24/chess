package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;
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

  public void joinGame(PlayerInfo playerInfo,UserData user) throws DuplicateException, BadRequestException, DataAccessException{
    var game = games.get(playerInfo.gameID());
    if (game == null){
      throw new BadRequestException("Error: game does not exist");
    }
    if (playerInfo.playerColor() != null){
      if (playerInfo.playerColor().equals("WHITE")){
        if (game.whiteUsername() == null){
          GameData newGame = new GameData(game.gameID(), user.username(),game.blackUsername(),game.gameName(),game.game());
          games.remove(game);
          games.put(newGame.gameID(),newGame);
        }
        else{
          throw new DuplicateException("Error: white team taken");
        }
      }
      else if (playerInfo.playerColor().equals("BLACK")){
        if (game.blackUsername() == null) {
          GameData newGame = new GameData(game.gameID(), game.whiteUsername(),user.username(),game.gameName(),game.game());
          games.remove(game);
          games.put(newGame.gameID(),newGame);
        }
        else{
          throw new DuplicateException("Error: black team taken");
        }
      }
      else{
        throw new DataAccessException("Error: unable to join game");
      }
    }
  }


  public GameList listGamesArray(){
    ArrayList<GameData> newGames = new ArrayList<>();
    for (GameData game: games.values()){
      newGames.add(game);
    }
    GameList games = new GameList(newGames);
    return games;
  }
  public Integer lengthOfGames(){
    return games.size();
  }
}
