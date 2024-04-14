package webSocketMessages.userCommands;

import chess.ChessGame;

public class Leave extends UserGameCommand {
  private Integer gameID;
  public Leave(String authToken,Integer gameID){
    super(authToken);
    this.gameID = gameID;
    setCommandType(UserGameCommand.CommandType.LEAVE);
  }

  public Integer getGameID(){
    return this.gameID;
  }
}
