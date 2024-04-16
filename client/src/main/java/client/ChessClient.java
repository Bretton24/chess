package client;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import model.AuthData;
import model.GameData;
import model.PlayerInfo;
import model.UserData;
import server.ServerFacade;
import ui.ChessboardDrawing;
import webSocket.ServerMessageHandler;
import webSocket.WebSocketFacade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
  private WebSocketFacade ws;
  private ServerMessageHandler serverMessageHandler;
  private final Map<Character, Integer> whiteDictionary = new HashMap<>();
  private HashMap<Character,Integer> dictionary = new HashMap<>();
  public ChessClient(String serverUrl,ServerMessageHandler serverMessageHandler){
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
    this.serverMessageHandler = serverMessageHandler;
    initializeDictionaries();
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
        case "highlight" -> highlightMoves(params);
        case "resign" -> resign();
        case "move" -> move(params);
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

  public String resign() throws Exception{
    Scanner scanner = new Scanner(System.in);
    System.out.println("Are you sure you want to resign? (yes/no)");
    String response = scanner.nextLine().trim().toLowerCase();

    if (response.equals("yes")) {
      assertPlaying();
      ws.resign(authToken.authToken(), game.gameID());
      playingState = State.OBSERVING;
      return "You have resigned from the game.";
    } else if (response.equals("no")) {
      return "Resignation canceled.";
    } else {
      return "Invalid response. Please type 'yes' or 'no'.";
    }
  }

  public String move(String ... params) throws Exception {
    if (params.length == 2){
      assertPlaying();
      String values = params[0];
      var startLetter = values.charAt(0);
      var digiNumber = values.charAt(1);
      var startNumber = digiNumber - '0';
      String otherValues = params[1];
      var endLetter = otherValues.charAt(0);
      digiNumber = otherValues.charAt(1);
      var endNumber = digiNumber - '0';
      if ((whiteDictionary.containsKey(startLetter) && startNumber > 0 && startNumber < 9) && (whiteDictionary.containsKey(endLetter) && endNumber > 0 && endNumber < 9)){
        if (user.username().equals(game.whiteUsername()) || user.username().equals(game.blackUsername())){
          var startPosition = new ChessPosition(startNumber,whiteDictionary.get(startLetter));
          var endPosition = new ChessPosition(endNumber,whiteDictionary.get(endLetter));
          var move = new ChessMove(startPosition,endPosition);
          ws.move(authToken.authToken(), game.gameID(), move);
          return String.format("");
        }
      }
    }
    throw new Exception("Not entered correctly");
  }

  public String redrawChessboard() throws Exception {
    assertPlaying();
    if (game.blackUsername() != null && game.blackUsername().equals(user.username())){
      board.drawChessboard(true,null);
      return String.format("Redrew the board");
    }else if (game.whiteUsername() != null && game.whiteUsername().equals(user.username())){
      board.drawChessboard(false,null);
      return String.format("Redrew the board");
    }
    throw new Exception("You must be playing to redraw the board");
  }

  private void initializeDictionaries() {
    // Initialize whiteDictionary
    whiteDictionary.put('a', 1);
    whiteDictionary.put('b', 2);
    whiteDictionary.put('c', 3);
    whiteDictionary.put('d', 4);
    whiteDictionary.put('e', 5);
    whiteDictionary.put('f', 6);
    whiteDictionary.put('g', 7);
    whiteDictionary.put('h', 8);

  }

  public String leaveGame() throws Exception {
      if (playingState == State.OBSERVING || playingState == State.PLAYING){
        playingState = State.NOTPLAYING;
        ws.leave(authToken.authToken(),game.gameID());
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
      if (ws == null) {
        ws=new WebSocketFacade(serverUrl, serverMessageHandler);
      }
      Integer gameNum=Integer.valueOf(params[0]);
      PlayerInfo playerInfo = new PlayerInfo(null,gameNum);
      game = server.observeGame(authToken,playerInfo);
      ws.observeGame(authToken.authToken(),game.gameID(),user);
    }
    return String.format("Successfully observing game.");
  }


  public String joinGame(String ... params) throws Exception {
    if (params.length == 2) {
      assertSignedIn();
      Integer gameNum=Integer.valueOf(params[0]);
      playingState = state.PLAYING;
      if (ws == null){
        ws = new WebSocketFacade(serverUrl,serverMessageHandler);
      }
      if (params[1].equals("white")){
        var playerInfo=new PlayerInfo("WHITE", gameNum);
        game = server.joinGame(authToken, playerInfo);
        ws.joinGame(authToken.authToken(),game.gameID(), ChessGame.TeamColor.WHITE,user);
      } else if (params[1].equals("black")) {
        var playerInfo = new PlayerInfo("BLACK",gameNum);
        game = server.joinGame(authToken, playerInfo);
        ws.joinGame(authToken.authToken(),game.gameID(), ChessGame.TeamColor.BLACK,user);

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

  public String highlightMoves(String ... params) throws Exception{
    assertPlaying();
    if (params.length == 1){
      String values = params[0];
      var letter = values.charAt(0);
      var digiNumber = values.charAt(1);
      var number = digiNumber - '0';
      if (whiteDictionary.containsKey(letter) && number > 0 && number < 9){
        if (game.blackUsername() != null && game.blackUsername().equals(user.username())){
          var pos = new ChessPosition(number,whiteDictionary.get(letter));
            board.drawChessboard(true, pos);
            return String.format("succesffuly highlighted");
        }
        else if (game.whiteUsername() != null && game.whiteUsername().equals(user.username())) {
          var pos=new ChessPosition(number, whiteDictionary.get(letter));
          board.drawChessboard(false, pos);
          return String.format("succesffuly highlighted");
        }
        throw new Exception("theres no piece at this position");
      }
      throw new Exception("Wrong input");

    }
    throw new Exception("Enter it as one word, example: A6 or B7");
  }

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
              - make move <STARTPOSITION> <ENDPOSITION>
              - resign
              - highlight <CHESSPOSITION> ex. A5 or C2
              """;
    }
  }


  private void assertSignedIn() throws Exception {
    if (state == State.LOGGEDOUT) {
      throw new Exception("You must sign in");
    }
  }

  private void assertPlaying() throws Exception {
    if (playingState != State.PLAYING) {
      throw new Exception("You must be playing or you have already resigned");
    }
  }
}
