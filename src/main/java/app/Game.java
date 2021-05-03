package app;

import enums.PrintBoardType;
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
    public static final int UNINITIALIZED_POSITION = -1;

    public final static int RETURN = 99;
    public final static int EXIT_GAME = 100;
    public final static int SAVE_AND_EXIT_GAME = 111;

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
            if (lookingForDrawByRepeatingPositionOr50PassiveMoves()) {
                break;
            }
            FileManager.saveGameToFileTxt(this);
            scanBoardAndSetLegalMovesForCurrentPlayer();

            loadPositions:
            {
                int firstPosition, secondPosition;
                do {
                    printBoard(Printer.NOT_SELCTED_FIGURE);

                    firstPosition = loadFirstPosition();
                    switch (firstPosition) {
                        case SAVE_AND_EXIT_GAME: {
                        }
                        case EXIT_GAME: {
                            exitGameConditions();
                            break loadPositions;
                        }
                        default: {
                            printBoard(firstPosition);
                            break;
                        }
                    }

                    secondPosition = loadSecondPosition();
                    switch (secondPosition) {
                        case RETURN: {
                            returnConditions();
                            break;
                        }
                        case SAVE_AND_EXIT_GAME: {
                        }
                        case EXIT_GAME: {
                            exitGameConditions();
                            break loadPositions;
                        }
                        default: {
                            Move.clearFigureStates(this.getBoard());
                            Move.move(this, firstPosition, secondPosition);
                            break;
                        }
                    }
                } while (secondPosition == RETURN);
            }

            if (isActiveGame()) {
                Move.clearSelectedFigureAndLegalMoves(this.board);
                if (Move.getPawnIsMovedOrFigureIsTaking()) {
                    FileManager.deleteCurrentBoardFile(this);
                    FileManager.setPassiveMoveCounter(0);
                }
                if (lookingForCheckMateOrDraw()) {
                    setActiveGame(false);
                }
            }
        }
    }

    private boolean lookingForDrawByRepeatingPositionOr50PassiveMoves() {
        FileManager.saveCurrentBoard(this, true);
        FileManager.scanCurrentBoard(this);
        return this.isDraw();
    }

    private void scanBoardAndSetLegalMovesForCurrentPlayer() {
        Move.scanBoardAndSetUnderPressureAndProtectedStates(this);
        Move.isKingCheck(this.board);
    }

    private void printBoard(int selectedFigurePosition) {
        Printer.printBoard(this, PrintBoardType.NUMBERS, selectedFigurePosition);
        Printer.printBoard(this, PrintBoardType.DEFAULT, selectedFigurePosition);
    }

    private void exitGameConditions() {
        this.setActiveGame(false);
        FileManager.deleteCurrentBoardFile(this);
    }

    private void returnConditions() {
        Move.clearFigureStates(this.getBoard());
        Move.clearUnderPressure(this.getBoard());
        Move.clearSelectedFigureAndLegalMoves(this.getBoard());
    }

    private boolean lookingForCheckMateOrDraw() {
        invertWhichPlayer();
        Move.scanBoardAndSetUnderPressureAndProtectedStates(this);
        Move.isKingCheck(this.board);
        Move.scanBoardAndFindCheckMateOrDraw(this);
        return this.isCheckMate() || this.isDraw();
    }

    private int loadFirstPosition() {
        int firstPosition = 0;

        boolean thereAreNoExceptions;
        do {
            thereAreNoExceptions = true;
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Podaj liczbę [0-63 wybór figury / 100 wyjdź]: ");
                firstPosition = scanner.nextInt();
                if (!Move.isLegalFirstPosition(this, this.whichPlayer, firstPosition)) {
                    thereAreNoExceptions = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Niepoprawne dane wejściowe");
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
                System.out.print("Gdzie tą figurę położyć? [0-63 wybór figury / 99 wróć / 100 wyjdź]: ");
                secondPosition = scanner.nextInt();
                if (!Move.isLegalSecondPosition(this, this.whichPlayer, secondPosition)) {
                    thereAreNoExceptions = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Niepoprawne dane wejściowe");
                thereAreNoExceptions = false;
            }
        } while (!thereAreNoExceptions);

        return secondPosition;
    }

    public void invertWhichPlayer() {
        if (whichPlayer == FigureColor.WHITE) {
            whichPlayer = FigureColor.BLACK;
        } else {
            whichPlayer = FigureColor.WHITE;
        }
    }

    public void setWhichPlayer(FigureColor figureColor) {
        this.whichPlayer = figureColor;
    }

    public void setWhichPlayer(String figureColor) {
        if (figureColor.equals(FigureColor.WHITE.toString())) {
            this.whichPlayer = FigureColor.WHITE;
        } else {
            this.whichPlayer = FigureColor.BLACK;
        }
    }

    public void whoWon() {
        if (this.whichPlayer == FigureColor.BLACK) {
            System.out.println("Wygrał " + this.getPlayer1().getPlayerName());
        } else {
            System.out.println("Wygrał " + this.getPlayer2().getPlayerName());
        }
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
