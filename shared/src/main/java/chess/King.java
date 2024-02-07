package chess;

import java.util.HashSet;
import java.util.Objects;

public class King {
  private final ChessGame.TeamColor pieceColor;
  private HashSet<ChessMove> kingMove = new HashSet<>();
  private ChessPosition currentPosition;
  King(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    King king=(King) o;
    return pieceColor == king.pieceColor && Objects.equals(kingMove, king.kingMove) && Objects.equals(currentPosition, king.currentPosition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pieceColor, kingMove, currentPosition);
  }

  public HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition){
    for (int i =myPosition.getRow() - 1;i < myPosition.getRow() + 2;i++){
      for (int j =myPosition.getColumn() - 1; j < myPosition.getColumn() + 2; j++){
        if (i < 9 && j  < 9 && i > 0 && j > 0){
          ChessPosition position = new ChessPosition(i,j);
          if (position != myPosition){
            if (board.pieceAtPosition(position)){
              ChessPiece piece = board.getPiece(position);
              if(this.pieceColor != piece.getTeamColor()){
                kingMove.add(new ChessMove(myPosition,position));
              }
            }
            else{
              kingMove.add(new ChessMove(myPosition,position));
            }
          }
        }
      }
    }
    return kingMove;
  }

  public HashSet<ChessMove> getKingMove() {
    return kingMove;
  }
}
