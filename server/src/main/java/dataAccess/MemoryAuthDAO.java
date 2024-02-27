package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  private int nextId = 1;
  final private HashMap<String,AuthData> authTokens = new HashMap<>();

  public AuthData createAuth(UserData user) throws DataAccessException {
    AuthData authToken = new AuthData(UUID.randomUUID().toString(),user.username());
    if (!authTokens.containsKey(user.username())) {
      authTokens.put(user.username(), authToken);
      return authToken;
    }
    else{
      throw new DataAccessException("Error: authToken already exists.");
    }
  }


  public void deleteAuth(UserData user){
    if (authTokens.containsKey(user.username())){
      var authToken = authTokens.remove(user.username());
    }
  }
  @Override
  public void deleteAllAuthTokens() throws DataAccessException{
    authTokens.clear();
    if (!authTokens.isEmpty()){
      throw new DataAccessException("Error: authTokens not deleted");
    }
  }

  public Integer sizeOfAuthTokens(){
    return authTokens.size();
  }
}
