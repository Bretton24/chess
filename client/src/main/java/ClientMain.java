import chess.*;

public class ClientMain {
    public static void main(String[] args) {
      var serverUrl = "http://localhost:8080";
      if (args.length == 1) {
        serverUrl = args[0];
      }
      System.out.print("240 Chess Client");
    }
  }
