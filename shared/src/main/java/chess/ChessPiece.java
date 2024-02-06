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
            var bishop = new Bishop(pieceColor);
            return bishop.bishopMoves(board,myPosition);
        }
        else if (type == PieceType.KING){
            var king = new King(pieceColor);
            return king.kingMoves(board,myPosition);
        }
        else if (type == PieceType.KNIGHT){
            var knight = new Knight(pieceColor);
            return knight.knightMoves(board,myPosition);
        } else if (type == PieceType.PAWN) {
            var pawn = new Pawn(pieceColor);
            return pawn.pawnMoves(board,myPosition);
        } else if (type == PieceType.QUEEN) {
            var queen = new Queen(pieceColor);
            return queen.queenMoves(board,myPosition);
        } else if (type == PieceType.ROOK) {
            var rook = new Rook(pieceColor);
            return rook.rookMoves(board,myPosition);
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


    @Override
    public String toString() {
        String value = "";
        switch(this.getPieceType()){
            case KING -> {
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "K";
                }
                else{
                    value += "k";
                }
            }
            case QUEEN -> {
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "Q";
                }
                else{
                    value += "q";
                }
            }
            case BISHOP -> {
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "B";
                }
                else{
                    value += "b";
                }
            }
            case KNIGHT -> {
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "K";
                }
                else{
                    value += "k";
                }
            }
            case ROOK -> {
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "R";
                }
                else{
                    value += "r";
                }
            }
            case PAWN ->{
                if (pieceColor == ChessGame.TeamColor.WHITE){
                    value += "P";
                }
                else{
                    value += "p";
                }
            }
        }


        return value;
    }
}
