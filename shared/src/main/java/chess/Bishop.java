package chess;

import java.util.HashSet;

public class Bishop {
  private final ChessGame.TeamColor pieceColor;
  private HashSet<ChessMove> bishopMove = new HashSet<>();
  Bishop(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
  }

  public HashSet<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
    int i = myPosition.getRow() + 1;
    int j = myPosition.getColumn() + 1;
    while(i < 9 && j < 9) {
      ChessPosition position=new ChessPosition(i,j);
      i++;
      j++;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          bishopMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        bishopMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow() + 1;
    j = myPosition.getColumn() - 1;
    while(i < 9 && j > 0) {
      ChessPosition position=new ChessPosition(i,j);
      i++;
      j--;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          bishopMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        bishopMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow() - 1;
    j = myPosition.getColumn() - 1;
    while(i > 0 && j > 0) {
      ChessPosition position=new ChessPosition(i,j);
      i--;
      j--;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          bishopMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        bishopMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow() - 1;
    j = myPosition.getColumn() + 1;
    while(i > 0 && j < 9) {
      ChessPosition position=new ChessPosition(i,j);
      i--;
      j++;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          bishopMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        bishopMove.add(new ChessMove(myPosition, position));
      }
    }
    return bishopMove;
  }

  public HashSet<ChessMove> getBishopMove() {
    return bishopMove;
  }
}
