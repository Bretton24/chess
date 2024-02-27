package dataAccess;

import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  final private HashSet<AuthData> sessions = new HashSet<>();

  public AuthData createAuth(UserData user) throws DataAccessException {;
    AuthData authToken = new AuthData(UUID.randomUUID().toString(),user.username());
    sessions.add(authToken);
    return authToken;
  }

  public boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{
    for (AuthData token : sessions){
      if(token.authToken().equals(newAuthToken)){
        return true;
      }
    }
    throw new UnauthorizedAccessException("Error: not logged in");
  }


  public void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{
    boolean found = false;
    for (AuthData value: sessions){
      if (value.authToken().equals(newAuthToken)){
        sessions.remove(value);
        found = true;
      }
      if(sessions.contains(value) && found){
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
