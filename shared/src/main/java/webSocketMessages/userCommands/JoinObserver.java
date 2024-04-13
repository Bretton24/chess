package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserver extends UserGameCommand{

  private Integer gameID;
  public JoinObserver(String authToken,Integer gameID){
    super(authToken);
    setCommandType(CommandType.JOIN_OBSERVER);
    this.gameID = gameID;
  }

  public Integer getGameID(){
    return this.gameID;
  }
}
