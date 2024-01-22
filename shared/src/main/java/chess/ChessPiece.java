package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (type == PieceType.BISHOP){
            return bishopMoves(board,myPosition);
        }
        else if (type == PieceType.KING){
            return kingMoves(board,myPosition);
        }
        else if (type == PieceType.KNIGHT){
            return knightMoves(board,myPosition);
        }
        else if (type == PieceType.PAWN){
            return pawnMoves(board,myPosition);
        }
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that=(ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    private HashSet<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> bishopMove = new HashSet<>();
        int i = myPosition.getRow() + 1;
        int j = myPosition.getColumn() + 1;
        while(i < 9 && j < 9) {
            ChessPosition position=new ChessPosition(i,j);
            i++;
            j++;
            if (board.pieceAtPosition(position)) {
                ChessPiece piece =board.getPiece(position);
                if (this.pieceColor != piece.pieceColor){
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
                if (this.pieceColor != piece.pieceColor){
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
                if (this.pieceColor != piece.pieceColor){
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
                if (this.pieceColor != piece.pieceColor){
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

    private HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> kingMove = new HashSet<>();
        for (int i =myPosition.getRow() - 1;i < myPosition.getRow() + 2;i++){
            for (int j =myPosition.getColumn() - 1; j < myPosition.getColumn() + 2; j++){
                if (i < 9 && j  < 9 && i > 0 && j > 0){
                    ChessPosition position = new ChessPosition(i,j);
                    if (position != myPosition){
                        if (board.pieceAtPosition(position)){
                            ChessPiece piece = board.getPiece(position);
                            if(this.pieceColor != piece.pieceColor){
                                board.removePiece(position);
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

    private HashSet<ChessMove> knightMoves(ChessBoard board,ChessPosition myPosition){
        HashSet<ChessMove> knightMove = new HashSet<>();
        int i =myPosition.getRow() - 2;
        while (i < myPosition.getRow() + 3){
            for (int j =myPosition.getColumn() - 1;j < myPosition.getColumn() + 2;j += 2){
                if (i < 9 && j  < 9 && i > 0 && j > 0){
                    ChessPosition position = new ChessPosition(i,j);
                    if (position != myPosition){
                        if (board.pieceAtPosition(position)){
                            ChessPiece piece = board.getPiece(position);
                            if(this.pieceColor != piece.pieceColor){
                                board.removePiece(position);
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
                            if(this.pieceColor != piece.pieceColor){
                                board.removePiece(position);
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

    private HashSet<ChessMove> pawnMoves(ChessBoard board,ChessPosition myPosition){
        HashSet<ChessMove> pawnMove = new HashSet<>();
        
    }
}
