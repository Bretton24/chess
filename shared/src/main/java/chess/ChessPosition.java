package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int row;
    private final int col;
    private final int edgeBottom  = 1;
    private final int edgeTop = 8;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getEdgeBottom(){
        return this.edgeBottom;
    }
    public int getEdgeTop(){
        return this.edgeTop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition position=(ChessPosition) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }

    public String toString(){
        String value = "[" + getRow() + "][" + getColumn() + "]";
        return value;
    }
}
