package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
  private Integer gameID;
  public Resign(String authToken,Integer gameID){
    super(authToken);
    this.gameID = gameID;
    setCommandType(UserGameCommand.CommandType.LEAVE);
  }

  public Integer getGameID(){
    return this.gameID;
  }
}
