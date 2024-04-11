package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

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
      switch(row){
        case 0:
          row = 7;
          break;
        case 1:
          row = 6;
          break;
        case 2:
          row = 5;
          break;
        case 3:
          row = 4;
          break;
        case 4:
          row = 3;
          break;
        case 5:
          row = 2;
          break;
        case 6:
          row = 1;
          break;
        case 7:
          row = 0;
          break;
      }
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
        if ((col + row) % 2 == 0){
          setWhite(out);
          if (row == 0){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_WHITE);
            out.print(EMPTY.repeat(prefixLength));

            out.print(SET_TEXT_COLOR_RED);

            var piece = chessGame.getBoard().getPiece(new ChessPosition(row + 1,col));
            out.print(piece.toString());
            setWhite(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 1){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_WHITE);
            out.print(EMPTY.repeat(prefixLength));

            out.print(SET_TEXT_COLOR_RED);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)).toString());
            setWhite(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 6){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_WHITE);
            out.print(EMPTY.repeat(prefixLength));

            out.print(SET_TEXT_COLOR_BLUE);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setWhite(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 7){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_WHITE);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_BLUE);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setWhite(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else{
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
          }
        }
        else{
          if (row == 0){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_BLACK);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_RED);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setBlack(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 1){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_BLACK);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_RED);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setBlack(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 6){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_BLACK);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_BLUE);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setBlack(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else if (row == 7){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_BLACK);
            out.print(EMPTY.repeat(prefixLength));
              out.print(SET_TEXT_COLOR_BLUE);

//              out.print(SET_TEXT_COLOR_RED);

            out.print(chessGame.getBoard().getPiece(new ChessPosition(row + 1,col)));
            setBlack(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else{
            setBlack(out);
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
          }
        }
      }
    }
    out.println();
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
