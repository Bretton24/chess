package dataAccess;

public class MemoryGameDAO implements GameDAO{
  @Override
  public void deleteAllGames() {
    System.out.println("deleting games");
  }
}
