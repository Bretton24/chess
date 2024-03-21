package client;
import server.ServerFacade;

public class ChessClient {
  private final ServerFacade server;
  private final String serverUrl;
  private State state = State.LOGGEDOUT;
  public ChessClient(String serverUrl){
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }

  public String eval(String input){
    //nothing yet
    return "Nothing added";
  }
}
