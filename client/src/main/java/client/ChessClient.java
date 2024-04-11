package client;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
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
  private State playingState = State.NOTPLAYING;
  private GameData game;
  private UserData user;
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
        case "leave" -> leaveGame();
//        case "highlight moves" -> highlightMoves();
        case "redraw" -> redrawChessboard();
        case "quit" -> "quit";
        default -> help();
      };

    }catch (Exception ex) {
      return ex.getMessage();
    }
  }


  public String register(String ... params) throws Exception{
    if (params.length == 3){
      user = new UserData(params[0],params[1],params[2]);
      authToken = server.registerUser(user);
      state = State.LOGGEDIN;
      visitorName = params[0];
      return String.format("You registered and signed in as %s.",visitorName);
    }
    throw new Exception("Need more info.");
  }

  public String redrawChessboard() throws Exception {
    assertPlaying();
    if (game.blackUsername() != null && game.blackUsername().equals(user.username())){
      board.drawChessboard(true,game.game());
    }else if (game.whiteUsername() != null && game.whiteUsername().equals(user.username())){
      board.drawChessboard(false,game.game());
    }
    throw new Exception("You must be playing to redraw the board");
  }

  public String leaveGame() throws Exception {
      if (playingState == State.OBSERVING || playingState == State.PLAYING){
        playingState = State.NOTPLAYING;
        return String.format("Successfully left game");
      }
     throw new Exception("You're not playing or observing. Join or observe to leave.");
  }

  public String login(String ... params) throws Exception{
    if (params.length == 2){
      user = new UserData(params[0],params[1],null);
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
    for (GameData game: games.games()){
      System.out.println(game);
    }
    return String.format("Games listed above.");
  }

  public String observeGame(String ... params) throws Exception{
    if (params.length == 1){
      assertSignedIn();
      playingState = State.OBSERVING;
      Integer gameNum=Integer.valueOf(params[0]);
      PlayerInfo playerInfo = new PlayerInfo(null,gameNum);
      game = server.observeGame(authToken,playerInfo);
      board.drawChessboard(false,game.game());
    }
    return String.format("Successfully observing game.");
  }


  public String joinGame(String ... params) throws Exception {
    if (params.length == 2) {
      assertSignedIn();
      Integer gameNum=Integer.valueOf(params[0]);
      playingState = state.PLAYING;
      if (params[1].equals("white")){
        var playerInfo=new PlayerInfo("WHITE", gameNum);
        game = server.joinGame(authToken, playerInfo);
        board.drawChessboard(false,game.game());
      } else if (params[1].equals("black")) {
        var playerInfo = new PlayerInfo("BLACK",gameNum);
        game = server.joinGame(authToken, playerInfo);
        board.drawChessboard(true,game.game());
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

//  public String highlightMoves() throws Exception{
//    assertPlaying();
//
//  }

  public String help() {
    if (state == State.LOGGEDOUT) {
      return """
              - help
              - register <username> <password> <email>
              - login <username> <password>
              - quit
              """;
    } else if ((playingState == State.NOTPLAYING) && (state == State.LOGGEDIN)) {
      return """
              - create <GameName>
              - join <ID> <[WHITE|BLACK|<empty]
              - observe <ID> 
              - list
              - logout
              - quit
              """;
    } else {
      return """
              - help
              - redraw board
              - leave
              - make move
              - resign
              - highlight legal moves
              """;
    }
  }


  private void assertSignedIn() throws Exception {
    if (state == State.LOGGEDOUT) {
      throw new Exception("You must sign in");
    }
  }

  private void assertPlaying() throws Exception {
    if (playingState == State.NOTPLAYING) {
      throw new Exception("You must sign in");
    }
  }

  private void assertObserving() throws Exception {
    if (playingState != State.OBSERVING) {
      throw new Exception("You're not an observer");
    }
  }
}
