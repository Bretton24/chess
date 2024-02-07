package chess;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class Queen {
  private final ChessGame.TeamColor pieceColor;
  private final Rook rook;
  private final Bishop bishop;
  private HashSet<ChessMove> queenMove;

  Queen(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
     rook = new Rook(this.pieceColor);
     bishop = new Bishop(this.pieceColor);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Queen queen=(Queen) o;
    return pieceColor == queen.pieceColor && Objects.equals(rook, queen.rook) && Objects.equals(bishop, queen.bishop) && Objects.equals(queenMove, queen.queenMove);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pieceColor, rook, bishop, queenMove);
  }

  public HashSet<ChessMove> getQueenMove() {
    return queenMove;
  }

  public HashSet<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition){
    HashSet<ChessMove> queenMove = bishop.bishopMoves(board,myPosition);
    HashSet<ChessMove> moves = rook.rookMoves(board,myPosition);
    Iterator<ChessMove> iterator = moves.iterator();
    while(iterator.hasNext()){
      queenMove.add(iterator.next());
    }
    return  queenMove;
  }
}
