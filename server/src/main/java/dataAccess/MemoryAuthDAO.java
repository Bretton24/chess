package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
  private int nextId = 1;
  final private HashMap<Integer, AuthData> authTokens = new HashMap<>();
  @Override
  public void deleteAllAuthTokens() {
    authTokens.clear();
  }
}
