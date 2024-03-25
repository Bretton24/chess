package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessboardDrawing {

  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 3;
  private static final String EMPTY = " ";

  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    drawHeaders(out);

    drawChessboard(out);

    drawHeaders(out);

//    out.print(SET_BG_COLOR_BLACK);
//    out.print(SET_TEXT_COLOR_WHITE);
  }



  private static void drawHeaders(PrintStream out) {

    setGrey(out);

    String[] headers = {" ","h", "g", "f", "e", "d", "c", "b","a"," "};
    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 1; ++boardCol) {

      drawHeader(out, headers[boardCol]);

      out.print(EMPTY.repeat(0));

    }
    out.println();
  }

  private static void drawChessboard(PrintStream out) {
    for (int row=0; row < BOARD_SIZE_IN_SQUARES; row++) {
      drawRowOfSquares(out,row);
    }
//    out.println();
  }


  private static void drawRowOfSquares(PrintStream out,Integer row) {
    for (int col=0; col <= BOARD_SIZE_IN_SQUARES + 1; col++) {
      if (col == 0 || col == 9){
        setGrey(out);
        int prefixLength = 1;
        int suffixLength = 1;
        out.print(EMPTY.repeat(prefixLength));
        out.print(row + 1);
        out.print(EMPTY.repeat(suffixLength));
      }
      else{
        if ((col + row) % 2 == 0){
          setWhite(out);
          if (row == 1 || row == 6){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_WHITE);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_BLACK);

            out.print("P");

            setWhite(out);
            out.print(EMPTY.repeat(suffixLength));
          }
          else{
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
          }
        }
        else{
          if (row == 1 || row == 6){
            int prefixLength = 1;
            int suffixLength = 1;
            out.print(SET_BG_COLOR_BLACK);
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_WHITE);

            out.print("P");

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
