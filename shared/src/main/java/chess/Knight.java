package chess;

import java.util.HashSet;
import java.util.Objects;

public class Knight {
  private final ChessGame.TeamColor pieceColor;
  private HashSet<ChessMove> knightMove = new HashSet<>();
  Knight(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Knight knight=(Knight) o;
    return pieceColor == knight.pieceColor && Objects.equals(knightMove, knight.knightMove);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pieceColor, knightMove);
  }

  public HashSet<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition){
    int i =myPosition.getRow() - 2;
    while (i < myPosition.getRow() + 3){
      for (int j =myPosition.getColumn() - 1;j < myPosition.getColumn() + 2;j += 2){
        if (i < 9 && j  < 9 && i > 0 && j > 0){
          ChessPosition position = new ChessPosition(i,j);
          if (position != myPosition){
            if (board.pieceAtPosition(position)){
              ChessPiece piece = board.getPiece(position);
              if(this.pieceColor != piece.getTeamColor()){
                knightMove.add(new ChessMove(myPosition,position));
              }
            }
            else{
              knightMove.add(new ChessMove(myPosition,position));
            }
          }
        }
      }
      i += 4;
    }
    int j =myPosition.getColumn() - 2;
    while (j < myPosition.getColumn() + 3){
      for (i =myPosition.getRow() - 1;i < myPosition.getRow() + 2;i += 2){
        if (i < 9 && j  < 9 && i > 0 && j > 0){
          ChessPosition position = new ChessPosition(i,j);
          if (position != myPosition){
            if (board.pieceAtPosition(position)){
              ChessPiece piece = board.getPiece(position);
              if(this.pieceColor != piece.getTeamColor()){
                knightMove.add(new ChessMove(myPosition,position));
              }
            }
            else{
              knightMove.add(new ChessMove(myPosition,position));
            }
          }
        }
      }
      j += 4;
    }
    return knightMove;
  }

  public HashSet<ChessMove> getKnightMove() {
    return knightMove;
  }
}
