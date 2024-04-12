package webSocketMessages.userCommands;

import chess.ChessGame;
import model.AuthData;
import model.PlayerInfo;

public class JoinPlayer extends UserGameCommand{
  private Integer gameID;
  private ChessGame.TeamColor playerColor;
  JoinPlayer(String authToken,Integer gameID, ChessGame.TeamColor playerColor){
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
  }
}
