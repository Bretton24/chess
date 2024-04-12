package client;

import webSocket.ServerMessageHandler;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements ServerMessageHandler {

  private final ChessClient client;
  public Repl(String serverUrl){
    client = new ChessClient(serverUrl,this);
  }

  public void run(){
    System.out.println(BLACK_KING + "Welcome to Chess. Register or Login to play.");
    System.out.print(client.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")){
      printPrompt();
      String line = scanner.nextLine();

      try {
        result = client.eval(line);
        System.out.print(SET_TEXT_COLOR_BLUE + result);
      } catch(Throwable e){
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println("\nSee ya ...");
  }

  private void printPrompt() {
    System.out.print("\n" + RESET_TEXT_COLOR +  ">>> " + SET_TEXT_COLOR_GREEN);
  }

  @Override
  public void notify(ServerMessageHandler serverMessageHandler) {
    System.out.println(SET_TEXT_COLOR_BLUE + serverMessageHandler.toString());
    printPrompt();
  }
}
