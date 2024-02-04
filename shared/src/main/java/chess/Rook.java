package chess;

import java.util.HashSet;

public class Rook {
  private final ChessGame.TeamColor pieceColor;
  private HashSet<ChessMove> rookMove = new HashSet<>();
  Rook(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
  }

  public HashSet<ChessMove> getRookMove() {
    return rookMove;
  }

  public HashSet<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
    int i = myPosition.getRow() + 1;
    int j = myPosition.getColumn();
    while(i < 9) {
      ChessPosition position=new ChessPosition(i,j);
      i++;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          rookMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        rookMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow();
    j = myPosition.getColumn() + 1;
    while(j < 9) {
      ChessPosition position=new ChessPosition(i,j);
      j++;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          rookMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        rookMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow();
    j = myPosition.getColumn() - 1;
    while(j > 0) {
      ChessPosition position=new ChessPosition(i,j);
      j--;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          rookMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        rookMove.add(new ChessMove(myPosition, position));
      }
    }
    i = myPosition.getRow() - 1;
    j = myPosition.getColumn();
    while(i > 0) {
      ChessPosition position=new ChessPosition(i,j);
      i--;
      if (board.pieceAtPosition(position)) {
        ChessPiece piece =board.getPiece(position);
        if (this.pieceColor != piece.getTeamColor()){
          board.removePiece(position);
          rookMove.add(new ChessMove(myPosition,position));
        }
        break;
      } else {
        rookMove.add(new ChessMove(myPosition, position));
      }
    }
    return rookMove;
  }

  private boolean addMove(ChessBoard board,ChessPosition position) {
    if (board.pieceAtPosition(position)) {
      var piece=board.getPiece(position);
      if (piece.getTeamColor() != pieceColor) {
        return true;
      }
    }
    return false;
  }

}
