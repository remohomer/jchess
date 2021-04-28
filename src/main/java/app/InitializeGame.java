package app;

import booleans.CastlingConditions;
import enums.FigureColor;
import figures.Empty;
import figures.Figure;

public class InitializeGame {

    public static Game newGame() {
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
        Player player1 = new Player("Player 1", FigureColor.WHITE);
        Player player2 = new Player("Player 2", FigureColor.BLACK);
        Game game = new Game(board,player1,player2);
        FileManager.deleteCurrentBoardFile(game);
        return game;
    }

    public static Game newGame(String player1Name, String player2Name) {
        Game game = new Game(newGame());
        game.getPlayer1().setPlayerName(player1Name);
        game.getPlayer2().setPlayerName(player2Name);
        return game;
    }
}