package app;

import enums.FigureColor;
import booleans.GameStatus;

import java.util.Scanner;


public class Game extends GameStatus {
    private Player player1;
    private Player player2;
    private Board board;
    private FigureColor whichPlayer;


    public Game(Board board) {
        this.board = board;
        this.whichPlayer = FigureColor.WHITE;
        player1 = new Player("PLAYER_1", FigureColor.WHITE);
        player2 = new Player("PLAYER_2", FigureColor.BLACK);
    }

    public Game(Board board, String player1, String player2) {
        this(board);
        this.player1 = new Player(player1, FigureColor.WHITE);
        this.player2 = new Player(player2, FigureColor.BLACK);
    }

    public Game(Game game) {
        this.board = game.getBoard();
        this.whichPlayer = FigureColor.WHITE;
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
    }

    public void newGame() {
        this.setActiveGame(true);
        while (this.isActiveGame()) {

            Printer.printBoardWithNumbers(this);
            Printer.printBoard(this);

            int firstPosition = loadFirstPosition();
            this.board.getField(firstPosition).getFigure().movement(this, firstPosition);

            Printer.printBoardWithNumbers(this);
            Printer.printBoardWhileFigureIsSelected(this);

            int secondPosition = loadSecondPosition();
            Move.clearFigureStates(this.getBoard());
            Move.move(this, firstPosition, secondPosition);

            Move.clearSelectedFigureAndLegalMoves(this.board);
            invertWhichPlayer();

            Move.scanBoardAndSetUnderPressureAndProtectedStates(this);
            Move.isKingCheck(this.board);
            Move.scanBoardAndFindCheckMateOrDraw(this);

            isGameOver();
        }
    }

    private int loadFirstPosition() {
        int firstPosition = 0;

        boolean thereAreNoExceptions;
        do {
            thereAreNoExceptions = true;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Którą figurę poruszyć [0-63]: ");
                firstPosition = scanner.nextInt();
                if (!Move.isLegalFirstPosition(this.board, this.whichPlayer, firstPosition)) {
                    thereAreNoExceptions = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Podaj liczbę całkowitą od 0 do 63");
                thereAreNoExceptions = false;
            }
        } while (!thereAreNoExceptions);

        return firstPosition;
    }

    private int loadSecondPosition() {
        int secondPosition = 0;

        boolean thereAreNoExceptions;
        do {
            thereAreNoExceptions = true;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Gdzie tą figurę położyć? [0-63]: ");
                secondPosition = scanner.nextInt();
                if (!Move.isLegalSecondPosition(this.board, this.whichPlayer, secondPosition)) {
                    thereAreNoExceptions = false;
                }

            } catch (Exception e) {
                System.out.println("ERROR: Podaj liczbę całkowitą od 0 do 63");
                thereAreNoExceptions = false;
            }
        } while (!thereAreNoExceptions);

        return secondPosition;
    }

    public void invertWhichPlayer() {
        if (whichPlayer == FigureColor.WHITE) whichPlayer = FigureColor.BLACK;
        else whichPlayer = FigureColor.WHITE;
    }

    public void isGameOver() {
        if (this.isCheckMate() || this.isDraw())
            setActiveGame(false);
    }

    public void whoWon() {
        if (this.whichPlayer == FigureColor.BLACK) {
            System.out.println("Wygrał " + this.getPlayer1().getPlayerName());
        } else
            System.out.println("Wygrał " + this.getPlayer2().getPlayerName());
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public FigureColor getWhichPlayer() {
        return whichPlayer;
    }


}
