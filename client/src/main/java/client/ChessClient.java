package client;
import model.AuthData;
import model.PlayerInfo;
import model.UserData;
import server.ServerFacade;
import ui.ChessboardDrawing;

import java.util.Arrays;

public class ChessClient {
  private final ServerFacade server;
  private String visitorName = null;
  private final String serverUrl;
  private AuthData authToken = null;
  private ChessboardDrawing board = new ChessboardDrawing();
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
        case "create" -> createGame(params);
        case "join" -> joinGame(params);
        case "list" -> listGames();
        case "observe" -> observeGame(params);
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
      authToken = server.registerUser(user);
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

  public String listGames() throws Exception{
    assertSignedIn();
    var games = server.listGame(authToken);
    System.out.println(games.toString());
    return String.format("strings");
  }

  public String observeGame(String ... params) throws Exception{
    if (params.length == 1){
      assertSignedIn();
      Integer gameNum=Integer.valueOf(params[0]);
      PlayerInfo playerInfo = new PlayerInfo(null,gameNum);
      server.observeGame(authToken,playerInfo);
      board.createChessboard(true);
    }
    return String.format("Successfully observing game.");
  }


  public String joinGame(String ... params) throws Exception {
    if (params.length == 2) {
      assertSignedIn();
      Integer gameNum=Integer.valueOf(params[0]);
      if (params[1].equals("white")){
        var playerInfo=new PlayerInfo("WHITE", gameNum);
        server.joinGame(authToken, playerInfo);
        board.createChessboard(true);
      } else if (params[1].equals("black")) {
        var playerInfo = new PlayerInfo("BLACK",gameNum);
        server.joinGame(authToken, playerInfo);
        board.createChessboard(false);
      }
    }
    else{
      throw new Exception("Need to specify team to join");
    }
    return String.format("Successfully joined game.");
  }

  public String createGame(String ... params) throws Exception{
    if (params.length == 1){
      assertSignedIn();
      var gameID = server.gameCreate(authToken,params[0]);
      return String.format("Created a game with GameID:" + gameID.gameID());
    }
    throw new Exception("need a game name");

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
                - list
                - logout
                - quit
                """;
  }


  private void assertSignedIn() throws Exception {
    if (state == State.LOGGEDOUT) {
      throw new Exception("You must sign in");
    }
  }
}
