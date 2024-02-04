package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor team;
    public ChessGame() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame=(ChessGame) o;
        return Objects.equals(board, chessGame.board) && team == chessGame.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, team);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(board.pieceAtPosition(startPosition)){
            var piece = board.getPiece(startPosition);
            var myPieceMoves = piece.pieceMoves(board,startPosition);
            for(int i = 1; i < 9;i++){
                for(int j = 1; j < 9; j++){
                    var position = new ChessPosition(i,j);
                    if(board.pieceAtPosition(position) ){
                        var maybeEnemy = board.getPiece(position);
                        if(maybeEnemy.getTeamColor() != piece.getTeamColor()){
                            if(position != startPosition){
                                var enemyMoves = maybeEnemy.pieceMoves(board,position);
                                var tempArray = myPieceMoves;
                                tempArray.retainAll(enemyMoves);
                                myPieceMoves.removeAll(tempArray);
                            }
                        }
                    }
                }
            }
            return myPieceMoves;
        }
        else{
            return null;
        }
    }





    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) {
        var piece = board.getPiece(move.getStartPosition());
        var validMoves = validMoves(move.getStartPosition());
        try{
            if(validMoves.contains(move.getEndPosition()) && piece.getTeamColor() == getTeamTurn()){
                board.addPiece(move.getEndPosition(),piece);
                board.removePiece(move.getStartPosition());
            }
            else{
                throw new InvalidMoveException("Invalid Move");
            }
        }catch(InvalidMoveException e){
            System.out.println("Caught exception: " + e.getMessage());
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

}
