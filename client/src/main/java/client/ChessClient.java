package client;
import model.AuthData;
import model.UserData;
import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
  private final ServerFacade server;
  private String visitorName = null;
  private final String serverUrl;
  private AuthData authToken = null;
  private State state = State.LOGGEDOUT;
  public ChessClient(String serverUrl){
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }

  public String eval(String input){
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params =Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd){
        case "register" -> register(params);
        case "login" -> login(params);
        case "logout" -> logout();
        case "quit" -> "quit";
        default -> help();
      };

    }catch (Exception ex) {
      return ex.getMessage();
    }
  }


  public String register(String ... params) throws Exception{
    if (params.length == 3){
      var user = new UserData(params[0],params[1],params[2]);
      authToken = server.addUser(user);
      state = State.LOGGEDIN;
      visitorName = params[0];
      return String.format("You registered and signed in as %s.",visitorName);
    }
    throw new Exception("Need more info.");
  }

  public String login(String ... params) throws Exception{
    if (params.length == 2){
      var user = new UserData(params[0],params[1],null);
      authToken = server.loginUser(user);
      state = State.LOGGEDIN;
      visitorName = params[0];
      return String.format("You signed in as %s.",visitorName);
    }
    throw new Exception("Need more info.");
  }

  public String logout() throws Exception{
    assertSignedIn();
    state = State.LOGGEDOUT;
    server.logoutUser(authToken);
    return String.format("%s left the game",visitorName);
  }

  public String help() {
    if (state == State.LOGGEDOUT) {
      return """
                    - help
                    - register <username> <password> <email>
                    - login <username> <password>
                    - quit
                    """;
    }
    return """
                - create <GameName>
                - join <ID> <[WHITE|BLACK|<empty]
                - observe <ID> 
                - adoptAll
                - signOut
                - quit
                """;
  }


  private void assertSignedIn() throws Exception {
    if (state == State.LOGGEDOUT) {
      throw new Exception("You must sign in");
    }
  }
}
