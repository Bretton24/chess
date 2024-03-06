package dataAccess;

import model.AuthData;
import model.UserData;

public class SQLAuthDAO implements AuthDAO {
  @Override
  public AuthData createAuth(UserData user) throws DataAccessException {
    return new AuthData("","");
  }

  @Override
  public UserData getUser(String newAuthToken) throws UnauthorizedAccessException{return new UserData("","","");}
 @Override
  public boolean authTokenPresent(String newAuthToken)throws UnauthorizedAccessException{return true;}
  @Override
  public void deleteAuth(String newAuthToken) throws UnauthorizedAccessException, DataAccessException{}
  @Override
  public void deleteAllAuthTokens() throws DataAccessException {}




}
