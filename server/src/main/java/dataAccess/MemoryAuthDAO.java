package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  private int nextId = 1;
  final private HashSet<AuthData> authTokens = new HashSet<>();

  public AuthData createAuth(UserData user){
    AuthData authToken = new AuthData(UUID.randomUUID().toString(),user.username());
    if (!authTokens.contains(authToken)){
      authTokens.add(authToken);
      return authToken;
    }

    return authToken;
  }
  @Override
  public void deleteAllAuthTokens() {
    authTokens.clear();
  }
}
