package webSocketMessages.serverMessages;

public class Notification extends ServerMessage{
  private String message;

  Notification(String message){
    super(ServerMessageType.NOTIFICATION);
    this.message = message;
  }
}
