package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessboardDrawing {

  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 3;
  private static final String EMPTY = " ";
  private static ChessGame chessGame = new ChessGame();

  public void createInitialChessboard(Boolean black) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    out.print(ERASE_SCREEN);
    if (black){
      drawHeaders(out,black);
      drawChessboard(out,black);
      drawHeaders(out,black);
    }
    else{
      drawHeaders(out,black);
      drawChessboard(out,black);
      drawHeaders(out,black);
    }
    out.print(RESET_BG_COLOR);

  }



  private static void drawHeaders(PrintStream out,Boolean black) {
    ChessBoard board = new ChessBoard();
    board.resetBoard();
    chessGame.setBoard(board);
    var piece = chessGame.getBoard().getPiece(new ChessPosition(2,1));
    var moves = chessGame.validMoves(new ChessPosition(2,1));
    var pos = new ChessPosition(3,1);
    if (moves.contains(new ChessMove(new ChessPosition(2,1),pos))){
      chessGame.getBoard().removePiece(new ChessPosition(2,1));
      chessGame.getBoard().addPiece(pos,piece);
    }
    setGrey(out);
    String[] headers;
    if (black) {
      headers = new String[]{" ", "h", "g", "f", "e", "d", "c", "b", "a"};
    } else {
      headers = new String[]{" ", "a", "b", "c", "d", "e", "f", "g", "h"};
    }

    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {

      drawHeader(out, headers[boardCol]);

      out.print(EMPTY.repeat(0));

    }
    out.println();
  }

  private static void drawChessboard(PrintStream out,Boolean black) {
    for (int row=0; row < BOARD_SIZE_IN_SQUARES; row++) {
      drawRowOfSquares(out,row,black);
    }
  }


  private static void drawRowOfSquares(PrintStream out,Integer row,Boolean black) {
    String[] numbers;
    if (black) {
      numbers = new String[]{"1","2","3","4","5","6","7","8"};
    } else {
      numbers = new String[]{"8","7","6","5","4","3","2","1"};
      row = 7 - row; // Adjust row numbering for white side
    }
    for (int col=0; col <= BOARD_SIZE_IN_SQUARES + 1; col++) {
      if (col == 0 || col == 9){
        setGrey(out);
        int prefixLength = 1;
        int suffixLength = 1;
        out.print(EMPTY.repeat(prefixLength));
        out.print(numbers[row]);
        out.print(EMPTY.repeat(suffixLength));
      }
      else{
        Boolean whiteSquare = (col + row) % 2 == 0;
        setSquareColor(out,whiteSquare);
        if (chessGame.getBoard().pieceAtPosition(new ChessPosition(row + 1,col))){
          int prefixLength = 1;
          int suffixLength = 1;
          if (whiteSquare){
            out.print(SET_BG_COLOR_WHITE);
          }else{
            out.print(SET_BG_COLOR_BLACK);
          }
          out.print(EMPTY.repeat(prefixLength));
          var piece = chessGame.getBoard().getPiece(new ChessPosition(row + 1,col));
          if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            out.print(SET_TEXT_COLOR_RED);
          }
          else{
            out.print(SET_TEXT_COLOR_BLUE);
          }
          out.print(piece.toString());
          if (whiteSquare){
            out.print(SET_BG_COLOR_WHITE);
          }else{
            out.print(SET_BG_COLOR_BLACK);
          }
          out.print(EMPTY.repeat(suffixLength));
        }
        else{
          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }
      }
    }
    out.println();
  }

  private static void setSquareColor(PrintStream out, boolean isWhiteSquare) {
    if (isWhiteSquare) {
      setWhite(out);
    } else {
      setBlack(out);
    }
  }
  private static void drawHeader(PrintStream out, String headerText) {
    int prefixLength = 1;
    int suffixLength = 1;

    out.print(EMPTY.repeat(prefixLength));
    printHeaderText(out, headerText);
    out.print(EMPTY.repeat(suffixLength));
  }

  private static void printHeaderText(PrintStream out, String player) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);

    out.print(player);

    setGrey(out);
  }

  private static void setWhite(PrintStream out) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }

  private static void setGrey(PrintStream out) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
  }
}
