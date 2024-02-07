package chess;

import java.util.Collection;
import java.util.HashMap;
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
        var someMoves = new HashSet<ChessMove>() {
        };
        if(board.pieceAtPosition(startPosition)) {
            var piece=board.getPiece(startPosition);
            var moves=piece.pieceMoves(board, startPosition);
            for (ChessMove move : moves) {
                if (isValid(move)) {
                    someMoves.add(move);
                }
            }
            return someMoves;
        }
        return null;
    }

    private ChessBoard copyChessboard(){
        ChessBoard copyBoard = new ChessBoard();
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                var position = new ChessPosition(i,j);
                if(board.pieceAtPosition(position)){
                    copyBoard.addPiece(position,board.getPiece(position));
                }
            }
        }
        return copyBoard;
    }

    private boolean isValid(ChessMove move){
        var originalBoard = this.board;
        var copyBoard = this.copyChessboard();
        var piece = copyBoard.getPiece(move.getStartPosition());
        copyBoard.removePiece(move.getStartPosition());
        copyBoard.addPiece(move.getEndPosition(),piece);
        this.setBoard(copyBoard);
        if(isInCheck(piece.getTeamColor())){
            this.setBoard(originalBoard);
            return false;
        }
        this.setBoard(originalBoard);
        return true;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean invalid = true;
        if(board.pieceAtPosition(move.getStartPosition())){
            var piece = board.getPiece(move.getStartPosition());
            var pieceMoves = this.validMoves(move.getStartPosition());
            if(piece.getTeamColor() == getTeamTurn()){
                for (ChessMove moveInPieceMoves : pieceMoves){
                    if(move.equals(moveInPieceMoves)){
                        if(move.getPromotionPiece() == null){
                            board.removePiece(move.getEndPosition());
                            board.addPiece(move.getEndPosition(),piece);
                            board.removePiece(move.getStartPosition());
                        }
                        else{
                            board.removePiece(move.getEndPosition());
                            board.addPiece(move.getEndPosition(),new ChessPiece(getTeamTurn(),move.getPromotionPiece()));
                            board.removePiece(move.getStartPosition());
                        }
                        invalid = false;
                    }
                }

            }
            if(invalid) {
                throw new InvalidMoveException();
            }else{
                if(getTeamTurn() == TeamColor.BLACK){
                    setTeamTurn(TeamColor.WHITE);
                }
                else{
                    setTeamTurn(TeamColor.BLACK);
                }
            }

        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for(int i = 1;i < 9;i++){
            for(int j = 1;j < 9;j++){
                var position = new ChessPosition(i,j);
                if(board.pieceAtPosition(position)){
                    var piece = board.getPiece(position);
                    if (piece.getTeamColor() != teamColor){
                        var enemyMoves = piece.pieceMoves(board,position);
                        var kingPosition = board.getKingPosition(teamColor);
                        for (ChessMove pos : enemyMoves){
                            if(kingPosition != null){
                                if(pos.getEndPosition().getRow() == kingPosition.getRow() && pos.getEndPosition().getColumn()  == kingPosition.getColumn() && position != kingPosition){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)){
            var pos = board.getKingPosition(teamColor);
            var valid = this.validMoves(pos);
            if (valid.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)){
            var pos = board.getKingPosition(teamColor);
            var valid = this.validMoves(pos);
            if (valid.isEmpty()) {
                return true;
            }
        }
        return false;
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
