package app;

import booleans.CastlingConditions;
import figures.Empty;
import figures.Figure;

public class InitializeGame {

    public static Game start() {
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
        return game;
    }

    public static Game start(String player1, String player2) {
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
        Game game = new Game(board, player1, player2);
        return game;
    }
}
