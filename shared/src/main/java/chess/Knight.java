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

  public HashSet<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
    HashSet<ChessMove> knightMove = new HashSet<>();

    int[][] moves = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}};

    for (int[] move : moves) {
      int newRow = myPosition.getRow() + move[0];
      int newColumn = myPosition.getColumn() + move[1];

      if (newRow >= 1 && newRow <= 8 && newColumn >= 1 && newColumn <= 8) {
        ChessPosition position = new ChessPosition(newRow, newColumn);
        if (!position.equals(myPosition)) {
          if (board.pieceAtPosition(position)) {
            ChessPiece piece = board.getPiece(position);
            if (this.pieceColor != piece.getTeamColor()) {
              knightMove.add(new ChessMove(myPosition, position));
            }
          } else {
            knightMove.add(new ChessMove(myPosition, position));
          }
        }
      }
    }

    return knightMove;
  }


  public HashSet<ChessMove> getKnightMove() {
    return knightMove;
  }
}
