package dataAccess;

public class MemoryUserDAO implements UserDAO{
  @Override
  public void deleteAllUsers() {
    System.out.println("deleting the users");
  }
}
