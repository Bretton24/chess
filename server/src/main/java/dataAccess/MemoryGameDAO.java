package dataAccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
  private int nextId = 1;
  final private HashMap<Integer,GameData> games = new HashMap<>();
  @Override
  public void deleteAllGames() {
    games.clear();
  }
}
