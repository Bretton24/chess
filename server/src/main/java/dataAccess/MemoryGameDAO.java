package dataAccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
  private int nextId = 1;
  final private HashMap<Integer,GameData> games = new HashMap<>();

  public GameData createGame(GameData game){
    game = new GameData(nextId++,game.whiteUsername(),game.blackUsername(),game.gameName(),game.game());
    games.put(game.gameID(),game);
    return game;
  }
  public GameData getGame(int gameID){
    return games.get(gameID);
  }
  @Override
  public void deleteAllGames() {
    games.clear();
  }
}
