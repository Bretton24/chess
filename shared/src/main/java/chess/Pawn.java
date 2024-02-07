package chess;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class Pawn {
  private final ChessGame.TeamColor pieceColor;
  private HashSet<ChessMove> pawnMove = new HashSet<>();
  Pawn(ChessGame.TeamColor pieceColor){
    this.pieceColor = pieceColor;
  }

  public HashSet<ChessMove> getPawnMove() {
    return pawnMove;
  }

  private HashSet<ChessMove> addPromotionPiece(ChessPosition myPosition, ChessPosition position){
    HashSet<ChessMove> pawnAdvance = new HashSet<>();
    if(this.pieceColor == ChessGame.TeamColor.WHITE){
      ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
      ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
      ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
      ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
      pawnAdvance.add(new ChessMove(myPosition,position,piece1.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece2.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece3.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece4.getPieceType()));
    }
    else{
      ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
      ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
      ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
      ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
      pawnAdvance.add(new ChessMove(myPosition,position,piece1.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece2.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece3.getPieceType()));
      pawnAdvance.add(new ChessMove(myPosition,position,piece4.getPieceType()));
    }
    return pawnAdvance;
  }
  private HashSet<ChessMove> forwardWhiteOne(ChessBoard board,ChessPosition myPosition){
    HashSet<ChessMove> pawnWhiteMoves = new HashSet<>();
    int i = myPosition.getRow() + 1;
    int j = myPosition.getColumn();
    ChessPosition position = new ChessPosition(i,j);
    if (i < 9) {
      if (!board.pieceAtPosition(position)) {
        if (position.getRow() == 8) {
          return addPromotionPiece(myPosition, position);
        } else {
          pawnWhiteMoves.add(new ChessMove(myPosition, position));
          return pawnWhiteMoves;
        }
      }
    }
    return pawnWhiteMoves;
  }

  private HashSet<ChessMove> forwardBlackOne(ChessBoard board,ChessPosition myPosition){
    HashSet<ChessMove> pawnBlackMoves = new HashSet<>();
    int i = myPosition.getRow() - 1;
    int j = myPosition.getColumn();
    ChessPosition position = new ChessPosition(i,j);
    if(i > 0) {
      if (!board.pieceAtPosition(position)) {
        if (position.getRow() == 1) {
          return addPromotionPiece(myPosition, position);
        } else {
          pawnBlackMoves.add(new ChessMove(myPosition, position));
          return pawnBlackMoves;
        }
      }
    }
    return pawnBlackMoves;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pawn pawn=(Pawn) o;
    return pieceColor == pawn.pieceColor && Objects.equals(pawnMove, pawn.pawnMove);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pieceColor, pawnMove);
  }

  private HashSet<ChessMove> attackLeft(ChessBoard board, ChessPosition myPosition){
    HashSet<ChessMove> attacksLeft = new HashSet<>();
    int i =myPosition.getRow();
    int j =myPosition.getColumn();
    if(pieceColor == ChessGame.TeamColor.WHITE){
      if(i+1 < 9 && j - 1 > 0){
        ChessPosition attackLeft = new ChessPosition(i+1,j-1);
        ChessPiece piece =board.getPiece(attackLeft);
        if (board.pieceAtPosition(attackLeft) && piece.getTeamColor() != ChessGame.TeamColor.WHITE){
          if (attackLeft.getRow() == 8){
            return addPromotionPiece(myPosition,attackLeft);
          }
          else{
            attacksLeft.add(new ChessMove(myPosition,attackLeft));
            return attacksLeft;
          }
        }
      }
    }
    else{
      if(i-1 > 0 && j + 1 < 9){
        ChessPosition attackLeft = new ChessPosition(i-1,j+1);
        ChessPiece piece =board.getPiece(attackLeft);
        if (board.pieceAtPosition(attackLeft) && piece.getTeamColor() != ChessGame.TeamColor.BLACK){
          if (attackLeft.getRow() == 1){
            return addPromotionPiece(myPosition,attackLeft);
          }
          else{
            attacksLeft.add(new ChessMove(myPosition,attackLeft));
            return attacksLeft;
          }
        }
      }
    }
    return attacksLeft;
  }
  private HashSet<ChessMove> attackRight(ChessBoard board,ChessPosition myPosition){
    HashSet<ChessMove> attacksRight = new HashSet<>();
    int i =myPosition.getRow();
    int j =myPosition.getColumn();
    if(pieceColor == ChessGame.TeamColor.WHITE){
      if(i+1 < 9 && j + 1 < 9){
        ChessPosition attackRight = new ChessPosition(i+1,j+1);
        ChessPiece piece =board.getPiece(attackRight);
        if (board.pieceAtPosition(attackRight) && piece.getTeamColor() != ChessGame.TeamColor.WHITE){
          if (attackRight.getRow() == 8){
            return addPromotionPiece(myPosition,attackRight);
          }
          else{
            attacksRight.add(new ChessMove(myPosition,attackRight));
            return attacksRight;
          }
        }
      }
    }
    else{
      if(i-1 > 0 && j - 1 > 0){
        ChessPosition attackRight = new ChessPosition(i-1,j-1);
        ChessPiece piece =board.getPiece(attackRight);
        if (board.pieceAtPosition(attackRight) && piece.getTeamColor() != ChessGame.TeamColor.BLACK){
          if (attackRight.getRow() == 1){
            return addPromotionPiece(myPosition,attackRight);
          }
          else{
            attacksRight.add(new ChessMove(myPosition,attackRight));
            return attacksRight;
          }
        }
      }
    }
    return attacksRight;
  }
  public HashSet<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition){
    int i = myPosition.getRow();
    int j =myPosition.getColumn();
    if (this.pieceColor == ChessGame.TeamColor.WHITE){
      HashSet<ChessMove> pawnWhiteMoves = forwardWhiteOne(board,myPosition);
      Iterator<ChessMove> pawnWhite =pawnWhiteMoves.iterator();
      while (pawnWhite.hasNext()){
        pawnMove.add(pawnWhite.next());
      }
      if (i == 2){
        ChessPosition closerPosition = new ChessPosition(i+1,j);
        ChessPosition position = new ChessPosition(i+2,j);
        if (!board.pieceAtPosition(position) && position.getRow() > 0 && !board.pieceAtPosition(closerPosition)){
          pawnMove.add(new ChessMove(myPosition,position));
        }
      }
      HashSet<ChessMove> attackRight = attackRight(board,myPosition);
      Iterator<ChessMove> attackrightiterator =attackRight.iterator();
      while (attackrightiterator.hasNext()){
        pawnMove.add(attackrightiterator.next());
      }
      HashSet<ChessMove> attackLeft = attackLeft(board,myPosition);
      Iterator<ChessMove> attackleftiterator =attackLeft.iterator();
      while (attackleftiterator.hasNext()){
        pawnMove.add(attackleftiterator.next());
      }
    }
    else{
      HashSet<ChessMove> pawnBlackMoves = forwardBlackOne(board,myPosition);
      Iterator<ChessMove> pawnBlack =pawnBlackMoves.iterator();
      while (pawnBlack.hasNext()){
        pawnMove.add(pawnBlack.next());
      }
      if (i == 7){
        ChessPosition closerPosition = new ChessPosition(i-1,j);
        ChessPosition position = new ChessPosition(i-2,j);
        if (!board.pieceAtPosition(position) && position.getRow() > 0 && !board.pieceAtPosition(closerPosition)){
          pawnMove.add(new ChessMove(myPosition,position));
        }
      }
      HashSet<ChessMove> attackRight = attackRight(board,myPosition);
      Iterator<ChessMove> attackrightiterator =attackRight.iterator();
      while (attackrightiterator.hasNext()){
        pawnMove.add(attackrightiterator.next());
      }
      HashSet<ChessMove> attackLeft = attackLeft(board,myPosition);
      Iterator<ChessMove> attackleftiterator =attackLeft.iterator();
      while (attackleftiterator.hasNext()){
        pawnMove.add(attackleftiterator.next());
      }
    }
    return pawnMove;
  }
}
