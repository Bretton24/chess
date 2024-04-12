package webSocketMessages.serverMessages;

public class LoadGame extends ServerMessage{
  private String game;
  LoadGame(String game){
    super(ServerMessageType.LOAD_GAME);
    this.game = game;
  }
}
