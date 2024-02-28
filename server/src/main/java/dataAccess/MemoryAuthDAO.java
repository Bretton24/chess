package dataAccess;

import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  final private HashMap<AuthData,UserData> sessions = new HashMap<>();

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

  public UserData getUser(String newAuthToken) throws UnauthorizedAccessException{
    for (AuthData token: sessions.keySet()){
      if(token.authToken().equals(newAuthToken)){
        return sessions.get(token);
      }
    }
    throw new UnauthorizedAccessException("Error: user does not exist");
  }

  public boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{
    for (AuthData token : sessions.keySet()){
      if(token.authToken().equals(newAuthToken)){
        return true;
      }
    }
    throw new UnauthorizedAccessException("Error: not logged in");
  }


  public void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{
    boolean found = false;
    for (AuthData value: sessions.keySet()){
      if (value.authToken().equals(newAuthToken)){
        sessions.remove(value);
        found = true;
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

  public Integer sizeOfAuthTokens(){
    return sessions.size();
  }
}
