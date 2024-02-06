package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn()-1] = piece;
    }

    public void removePiece(ChessPosition position){
        squares[position.getRow() - 1][position.getColumn()-1] = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(squares);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */

    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    public boolean pieceAtPosition(ChessPosition position){
        if (squares[position.getRow() - 1][position.getColumn() - 1] != null){
            return true;
        }
        else{
            return false;
        }
    }

    public ChessPosition getKingPosition(ChessGame.TeamColor teamColor){
        for(int i = 1;i < 9;i++){
            for(int j = 1;j < 9;j++){
                var position = new ChessPosition(i,j);
                if(this.pieceAtPosition(position)){
                    var piece = this.getPiece(position);
                    if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                        return position;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        int i = 2;
        int j = 1;
        while (j < 9){
            ChessPosition position = new ChessPosition(i,j);
            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            this.addPiece(position,piece);
            j++;
        }
        i = 7;
        j = 1;
        while (j < 9){
            ChessPosition position = new ChessPosition(i,j);
            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            this.addPiece(position,piece);
            j++;
        }
        i = 1;
        j = 1;
        while (j < 9){
            ChessPosition position = new ChessPosition(i,j);
            if (j == 1 || j == 8){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                this.addPiece(position,piece);
            }
            else if (j == 2 || j == 7){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                this.addPiece(position,piece);
            }
            else if (j == 3 || j == 6){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                this.addPiece(position,piece);
            }
            else if (j == 4){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                this.addPiece(position,piece);
            }
            else if (j == 5){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                this.addPiece(position,piece);
            }
            j++;
        }
        i = 8;
        j = 1;
        while (j < 9){
            ChessPosition position = new ChessPosition(i,j);
            if (j == 1 || j == 8){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                this.addPiece(position,piece);
            }
            else if (j == 2 || j == 7){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                this.addPiece(position,piece);
            }
            else if (j == 3 || j == 6){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                this.addPiece(position,piece);
            }
            else if (j == 4){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                this.addPiece(position,piece);
            }
            else if (j == 5){
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                this.addPiece(position,piece);
            }
            j++;
        }
    }

    @Override
    public String toString() {
        String value = "";
        for(int i = 8;i > 0;i--){
            for(int j = 1;j < 9;j++){
                ChessPosition position = new ChessPosition(i,j);
                if (this.pieceAtPosition(position)){
                    ChessPiece piece = this.getPiece(position);
                    value += "|" + piece.toString() + "|";
                }
                else{
                    value += "| |";
                }
            }
            value += "\n";
        }
        return value;
    }
}
