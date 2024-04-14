package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMove extends UserGameCommand{

  private Integer gameID;
  private ChessMove move;
  public MakeMove(String authToken, Integer gameID, ChessMove move){
    super(authToken);
    this.gameID = gameID;
    this.move = move;
    setCommandType(CommandType.MAKE_MOVE);
  }

  public Integer getGameID(){
    return this.gameID;
  }

  public ChessMove getMove(){
    return this.move;
  }
}
