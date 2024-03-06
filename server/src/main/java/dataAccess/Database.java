package dataAccess;

public class Database implements GameDAO,UserDAO,AuthDAO{
  @Override
  public int sizeOfAuthTokens() {
    return 0;
  }

  @Override
  public int lengthOfGames() {
    return 0;
  }

  @Override
  public Integer lengthOfUsers() {
    return null;
  }


}
