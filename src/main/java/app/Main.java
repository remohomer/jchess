package app;

import booleans.CastlingConditions;
import figures.*;

public class Main {

    public static void main(String[] args) {
        initializeGame();

    }

    public static void initializeGame() {
        Figure figure = new Empty();
        Field[] field = new Field[64];

        int row = 8;
        char ch = 97;
        for (int i = 0; i < 64; i++) {
            int column;
            if ((i + 1) % 8 == 0) {
                column = 8;
            } else {
                column = (i + 1) % 8;
            }
            field[i] = new Field(i, row, column, Character.toString(ch) + row, figure);
            if ((i + 1) % 8 == 0) {
                row--;
                ch -= 8;
            }
            ch++;
        }
        CastlingConditions castlingConditions = new CastlingConditions();

        Board board = new Board(castlingConditions, field);
        Game game = new Game(board);
        game.newGame();
    }

}