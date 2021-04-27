package app;

import enums.FigureColor;
import booleans.GameStatus;

import java.util.Scanner;


public class Game extends GameStatus {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private FigureColor whichPlayer;

    private static int counter = 1;
    protected final int id;


    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.whichPlayer = FigureColor.WHITE;
        this.player1 = player1;
        this.player2 = player2;
        id = counter++;
    }

    public Game(Game game) {
        this.board = game.getBoard();
        this.whichPlayer = game.getWhichPlayer();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.id = game.getId();
    }

    public void start() {
        this.setActiveGame(true);
        while (this.isActiveGame()) {

            FileManager.saveCurrentBoard(this);
            FileManager.scanCurrentBoard(this);

            if(this.isDraw()) {
                Printer.printBoard(this);
                System.out.println("REMIS przez powtórzenie ukłądu szachownicy");
                break;
            }

            Printer.printBoardWithNumbers(this);
            Printer.printBoard(this);

            Move.scanBoardAndSetUnderPressureAndProtectedStates(this);
            Move.isKingCheck(this.board);

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

    public void setWhichPlayer(FigureColor figureColor) {
        this.whichPlayer = figureColor;
    }

    public void setWhichPlayer(String figureColor) {
        if (figureColor.equals(FigureColor.WHITE.toString()))
            this.whichPlayer = FigureColor.WHITE;
        else
            this.whichPlayer = FigureColor.BLACK;
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

    public int getId() {
        return id;
    }


}
