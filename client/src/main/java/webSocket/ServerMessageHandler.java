package webSocket;
import webSocketMessages.serverMessages.*;
public interface ServerMessageHandler {
  void notify(ServerMessage serverMessage);
}
