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
        } else if (type == PieceType.PAWN) {
            return pawnMoves(board,myPosition);
        } else if (type == PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        } else if (type == PieceType.ROOK) {
            return rookMoves(board, myPosition);
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

    private HashSet<ChessMove> pawnMoves(ChessBoard board,ChessPosition myPosition) {
        HashSet<ChessMove> pawnMove = new HashSet<>();
        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (this.forwardWhiteOpen(board, myPosition)) {
                int i=myPosition.getRow() + 1;
                int j=myPosition.getColumn();
                ChessPosition position=new ChessPosition(i, j);
                if(this.upgrades(board,myPosition)){
                    ChessPosition upgradePosition = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                    ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                    ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                    ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                    ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                    pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                }
                else{
                    pawnMove.add(new ChessMove(myPosition, position));
                }
                if (this.initialWhite(board, myPosition)) {
                    int w=myPosition.getRow() + 2;
                    ChessPosition newPosition=new ChessPosition(w, j);
                    pawnMove.add(new ChessMove(myPosition, newPosition));
                }
            if (attackRightOrleft(board,myPosition)){
                int w = myPosition.getRow() + 1;
                int r = myPosition.getColumn() + 1;
                int l = myPosition.getColumn() - 1;
                ChessPosition attackRight = new ChessPosition(w,r);
                if (board.pieceAtPosition(attackRight)){
                    ChessPiece pieceRight = board.getPiece(attackRight);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.BLACK){
                        if(this.upgrades(board,myPosition)){
                            ChessPosition upgradePosition = new ChessPosition(w,r);
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                            ChessPosition nextPos = new ChessPosition(w, myPosition.getColumn());
                            if (!board.pieceAtPosition(nextPos)){
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece1.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece2.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece3.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece4.getPieceType()));
                            }
                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackRight));
                        }
                    }
                }
                ChessPosition attackLeft = new ChessPosition(w,l);
                if (board.pieceAtPosition(attackLeft)){
                    ChessPiece pieceRight = board.getPiece(attackLeft);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.BLACK){
                        if(this.upgrades(board,myPosition)){
                            ChessPosition upgradePosition = new ChessPosition(w,l);
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                            ChessPosition nextPos = new ChessPosition(w, myPosition.getColumn());
                            if (!board.pieceAtPosition(nextPos)){
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece1.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece2.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece3.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece4.getPieceType()));
                            }
                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackLeft));
                        }
                    }
                }
            }
            if (myPosition.getColumn() == 1){
                ChessPosition attackRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                if(board.pieceAtPosition(attackRight)){
                    ChessPiece pieceRight = board.getPiece(attackRight);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.BLACK){
                        if(this.upgrades(board,myPosition)){
                            ChessPosition upgradePosition = attackRight;
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                            ChessPosition nextPos = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                            if (!board.pieceAtPosition(nextPos)){
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece1.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece2.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece3.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece4.getPieceType()));
                            }
                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackRight));
                        }
                    }
                }
            }
                if (myPosition.getColumn() == 8){
                    ChessPosition attackLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
                    if(board.pieceAtPosition(attackLeft)){
                        ChessPiece pieceRight = board.getPiece(attackLeft);
                        if (pieceRight.pieceColor == ChessGame.TeamColor.BLACK){
                            if(this.upgrades(board,myPosition)){
                                ChessPosition upgradePosition = attackLeft;
                                ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                                ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                                ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                                ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                                pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                                ChessPosition nextPos = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                                if (!board.pieceAtPosition(nextPos)){
                                    pawnMove.add(new ChessMove(myPosition,nextPos,piece1.getPieceType()));
                                    pawnMove.add(new ChessMove(myPosition,nextPos,piece2.getPieceType()));
                                    pawnMove.add(new ChessMove(myPosition,nextPos,piece3.getPieceType()));
                                    pawnMove.add(new ChessMove(myPosition,nextPos,piece4.getPieceType()));
                                }
                            }
                            else {
                                pawnMove.add(new ChessMove(myPosition, attackLeft));
                            }
                        }
                    }
                }

            }
        }
        else if (this.getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (this.forwardBlackOpen(board, myPosition)) {
                int i=myPosition.getRow() - 1;
                int j=myPosition.getColumn();
                ChessPosition position=new ChessPosition(i, j);
                if (this.upgradesBlack(board, myPosition)) {
                    ChessPosition upgradePosition=new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
                    ChessPiece piece1=new ChessPiece(ChessGame.TeamColor.BLACK, PieceType.QUEEN);
                    ChessPiece piece2=new ChessPiece(ChessGame.TeamColor.BLACK, PieceType.KNIGHT);
                    ChessPiece piece3=new ChessPiece(ChessGame.TeamColor.BLACK, PieceType.BISHOP);
                    ChessPiece piece4=new ChessPiece(ChessGame.TeamColor.BLACK, PieceType.ROOK);
                    pawnMove.add(new ChessMove(myPosition, upgradePosition, piece1.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition, upgradePosition, piece2.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition, upgradePosition, piece3.getPieceType()));
                    pawnMove.add(new ChessMove(myPosition, upgradePosition, piece4.getPieceType()));
                } else {
                    pawnMove.add(new ChessMove(myPosition, position));
                }
                if (this.initialBlack(board, myPosition)) {
                    int w=myPosition.getRow() - 2;
                    ChessPosition newPosition=new ChessPosition(w, j);
                    pawnMove.add(new ChessMove(myPosition, newPosition));
                }
            }
            if (attackRightOrleft(board,myPosition)){
                int w = myPosition.getRow() - 1;
                int r = myPosition.getColumn() - 1;
                int l = myPosition.getColumn() + 1;
                ChessPosition attackRight = new ChessPosition(w,r);
                if (board.pieceAtPosition(attackRight)){
                    ChessPiece pieceRight = board.getPiece(attackRight);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.WHITE){
                        if(this.upgradesBlack(board,myPosition)){
                            ChessPosition upgradePosition = new ChessPosition(w,r);
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));

                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackRight));
                        }
                    }
                }
                ChessPosition attackLeft = new ChessPosition(w,l);
                if (board.pieceAtPosition(attackLeft)){
                    ChessPiece pieceLeft = board.getPiece(attackLeft);
                    if (pieceLeft.pieceColor == ChessGame.TeamColor.WHITE){
                        if(this.upgradesBlack(board,myPosition)){
                            ChessPosition upgradePosition = new ChessPosition(w,l);
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackLeft));
                        }
                    }
                }
            }
            if (myPosition.getColumn() == 8){
                ChessPosition attackRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
                if(board.pieceAtPosition(attackRight)){
                    ChessPiece pieceRight = board.getPiece(attackRight);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.BLACK){
                        if(this.upgradesBlack(board,myPosition)){
                            ChessPosition upgradePosition = attackRight;
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.WHITE,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));
                            ChessPosition nextPos = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                            if (!board.pieceAtPosition(nextPos)){
                                ChessPiece piece5 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.QUEEN);
                                ChessPiece piece6 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.KNIGHT);
                                ChessPiece piece7 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.BISHOP);
                                ChessPiece piece8 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.ROOK);
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece5.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece6.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece7.getPieceType()));
                                pawnMove.add(new ChessMove(myPosition,nextPos,piece8.getPieceType()));
                            }
                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackRight));
                        }
                    }
                }
            }
            if (myPosition.getColumn() == 1){
                ChessPosition attackLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                if(board.pieceAtPosition(attackLeft)){
                    ChessPiece pieceRight = board.getPiece(attackLeft);
                    if (pieceRight.pieceColor == ChessGame.TeamColor.WHITE){
                        if(this.upgradesBlack(board,myPosition)){
                            ChessPosition upgradePosition = attackLeft;
                            ChessPiece piece1 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.QUEEN);
                            ChessPiece piece2 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.KNIGHT);
                            ChessPiece piece3 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.BISHOP);
                            ChessPiece piece4 = new ChessPiece(ChessGame.TeamColor.BLACK,PieceType.ROOK);
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece1.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece2.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece3.getPieceType()));
                            pawnMove.add(new ChessMove(myPosition,upgradePosition,piece4.getPieceType()));

                        }
                        else {
                            pawnMove.add(new ChessMove(myPosition, attackLeft));
                        }
                    }
                }
            }

        }

        return pawnMove;
    }

    private boolean forwardWhiteOpen(ChessBoard board,ChessPosition myPosition){
        int i = myPosition.getRow();
        int j = myPosition.getColumn();
        ChessPosition position = new ChessPosition(i+1,j);
        if (i + 1 < 9 && !board.pieceAtPosition(position)){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean forwardBlackOpen(ChessBoard board,ChessPosition myPosition){
        int i = myPosition.getRow();
        int j = myPosition.getColumn();
        ChessPosition position = new ChessPosition(i-1,j);
        if (i - 1 > 0 && !board.pieceAtPosition(position)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean attackRightOrleft(ChessBoard board,ChessPosition myPosition){
        if (myPosition.getColumn() > 1 && myPosition.getColumn() < 8){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean initialWhite(ChessBoard board, ChessPosition myPosition){
        ChessPosition position = new ChessPosition(myPosition.getRow()+2,myPosition.getColumn());
        if (myPosition.getRow() == 2 && !board.pieceAtPosition(position)){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean initialBlack(ChessBoard board, ChessPosition myPosition){
        ChessPosition position = new ChessPosition(myPosition.getRow()-2,myPosition.getColumn());
        if (myPosition.getRow() == 7 && !board.pieceAtPosition(position)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean upgrades(ChessBoard board, ChessPosition myPosition){
        if (myPosition.getRow()+1 == 8){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean upgradesBlack(ChessBoard board, ChessPosition myPosition){
        if (myPosition.getRow()-1 == 1){
            return true;
        }
        else{
            return false;
        }
    }

    private HashSet<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> queenMove = bishopMoves(board,myPosition);
        HashSet<ChessMove> moves = rookMoves(board,myPosition);
        Iterator<ChessMove> iterator = moves.iterator();
        while(iterator.hasNext()){
            queenMove.add(iterator.next());
        }
        return  queenMove;
    }
    private HashSet<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> rookMove = new HashSet<>();
        int i = myPosition.getRow() + 1;
        int j = myPosition.getColumn();
        while(i < 9) {
            ChessPosition position=new ChessPosition(i,j);
            i++;
            if (board.pieceAtPosition(position)) {
                ChessPiece piece =board.getPiece(position);
                if (this.pieceColor != piece.pieceColor){
                    board.removePiece(position);
                    rookMove.add(new ChessMove(myPosition,position));
                }
                break;
            } else {
                rookMove.add(new ChessMove(myPosition, position));
            }
        }
        i = myPosition.getRow();
        j = myPosition.getColumn() + 1;
        while(j < 9) {
            ChessPosition position=new ChessPosition(i,j);
            j++;
            if (board.pieceAtPosition(position)) {
                ChessPiece piece =board.getPiece(position);
                if (this.pieceColor != piece.pieceColor){
                    board.removePiece(position);
                    rookMove.add(new ChessMove(myPosition,position));
                }
                break;
            } else {
                rookMove.add(new ChessMove(myPosition, position));
            }
        }
        i = myPosition.getRow();
        j = myPosition.getColumn() - 1;
        while(j > 0) {
            ChessPosition position=new ChessPosition(i,j);
            j--;
            if (board.pieceAtPosition(position)) {
                ChessPiece piece =board.getPiece(position);
                if (this.pieceColor != piece.pieceColor){
                    board.removePiece(position);
                    rookMove.add(new ChessMove(myPosition,position));
                }
                break;
            } else {
                rookMove.add(new ChessMove(myPosition, position));
            }
        }
        i = myPosition.getRow() - 1;
        j = myPosition.getColumn();
        while(i > 0) {
            ChessPosition position=new ChessPosition(i,j);
            i--;
            if (board.pieceAtPosition(position)) {
                ChessPiece piece =board.getPiece(position);
                if (this.pieceColor != piece.pieceColor){
                    board.removePiece(position);
                    rookMove.add(new ChessMove(myPosition,position));
                }
                break;
            } else {
                rookMove.add(new ChessMove(myPosition, position));
            }
        }
        return rookMove;
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
