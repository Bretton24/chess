import chess.*;
import server.Server;

public class Main {
  public static void main(String[] args){
    Server new_server = new Server();
    new_server.run(8080);
    System.out.println("240 Chess Server is running");
  }
}
