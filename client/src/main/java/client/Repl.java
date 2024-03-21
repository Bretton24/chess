package client;

public class Repl {

  private final ChessClient client;
  public Repl(String serverUrl){
    client = new ChessClient(serverUrl);
  }

  public void run(){
    System.out.println("\uD83D\uDC36 Welcome to the pet store. Sign in to start.");
  }
}
