package webSocket;
import webSocketMessages.serverMessages.*;
public interface ServerMessageHandler {
  void notify(ServerMessageHandler serverMessage);
}
