package webSocketMessages.userCommands;

import chess.ChessGame;
import model.AuthData;
import model.PlayerInfo;

public class JoinPlayer extends UserGameCommand{
  private Integer gameID;
  private ChessGame.TeamColor playerColor;
  public JoinPlayer(String authToken,Integer gameID, ChessGame.TeamColor playerColor){
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
  }

  public Integer getGameID(){
    return this.gameID;
  }

  public ChessGame.TeamColor getPlayerColor(){
    return this.playerColor;
  }


}
