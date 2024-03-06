package dataAccess;

import model.UserData;

public class SQLUserDAO implements UserDAO{

  @Override
  public void deleteAllUsers() throws DataAccessException {}
  @Override
  public UserData createUser(UserData data)throws DuplicateException,BadRequestException {return data;}
  @Override
  public UserData getUser(UserData user) throws BadRequestException,UnauthorizedAccessException{return user;}

  @Override
  public Integer lengthOfUsers() {
    return null;
  }

}
