package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  final private HashMap<AuthData,UserData> sessions = new HashMap<>();

  @Override
  public AuthData createAuth(UserData user) throws DataAccessException {;
    AuthData authToken = new AuthData(UUID.randomUUID().toString(),user.username());
    if (!sessions.containsKey(authToken)){
      sessions.put(authToken,user);
      return authToken;
    }
    else{
      throw new DataAccessException("Error: authToken already exists");
    }

  }

  @Override
  public UserData getUser(String newAuthToken) throws UnauthorizedAccessException{
    for (AuthData token: sessions.keySet()){
      if(token.authToken().equals(newAuthToken)){
        return sessions.get(token);
      }
    }
    throw new UnauthorizedAccessException("Error: user does not exist");
  }

  @Override
  public boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{
    for (AuthData token : sessions.keySet()){
      if(token.authToken().equals(newAuthToken)){
        return true;
      }
    }
    throw new UnauthorizedAccessException("Error: not logged in");
  }


  @Override
  public void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{
    boolean found = false;
    for (AuthData value: sessions.keySet()){
      if (value.authToken().equals(newAuthToken)){
         sessions.remove(value);
        found = true;
        break;
      }
      if(sessions.containsKey(value) && found){
        throw new DataAccessException("Error: authToken should've been removed");
      }
    }
    if (!found){
      throw new UnauthorizedAccessException("Error: unauthorized");
    }
  }
  @Override
  public void deleteAllAuthTokens() throws DataAccessException{
    sessions.clear();
    if (!sessions.isEmpty()){
      throw new DataAccessException("Error: authTokens not deleted");
    }
  }

  @Override
  public int sizeOfAuthTokens(){
    return sessions.size();
  }
}
