package webSocketMessages.serverMessages;

public class Error extends ServerMessage{
  private String errorMessage;
  Error(String errorMessage){
    super(ServerMessageType.ERROR);
    this.errorMessage = errorMessage;
  }
}
