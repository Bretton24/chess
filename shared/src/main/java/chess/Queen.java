package chess;

import java.util.HashSet;
import java.util.Iterator;

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
