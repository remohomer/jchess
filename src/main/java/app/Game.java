package app;

import enums.FigureColor;
import booleans.GameStatus;
import enums.PrintBoardType;

import java.util.Scanner;


public class Game extends GameStatus {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private FigureColor whoseTurn;
    private static int counter = 1;

    protected final int id;

    public final static int SAVE_AND_EXIT_GAME = 99;
    public final static int EXIT_GAME = 100;
    public final static int UNSELECT_FIGURE = 111;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.whoseTurn = FigureColor.WHITE;
        this.player1 = player1;
        this.player2 = player2;
        id = counter++;
    }

    public Game(Game game) {
        this.board = game.getBoard();
        this.whoseTurn = game.getWhoseTurn();
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
            scanBoardAndSetLegalMovesForCurrentPlayer();

            loadPositions:
            {
                int firstPosition, secondPosition;
                do {

                    Printer.printBoard(this, Printer.NOT_SELECTED_FIGURE, PrintBoardType.NUMBERS);

                    firstPosition = loadFirstPosition();
                    if (positionSwitcher(firstPosition)) {
                        break loadPositions;
                    }

                    Printer.printBoard(this, firstPosition, PrintBoardType.NUMBERS);

                    secondPosition = loadSecondPosition();
                    if (positionSwitcher(firstPosition, secondPosition)) {
                        break loadPositions;
                    }

                } while (secondPosition == UNSELECT_FIGURE);
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

    private boolean positionSwitcher(int position) {
        switch (position) {
            case UNSELECT_FIGURE: {
                returnConditions();
                return false;
            }
            case SAVE_AND_EXIT_GAME: {
                FileManager.saveGameToFileTxt(this);
                FileManager.saveGameToDataFile(this);
            }
            case EXIT_GAME: {
                exitGameConditions();
                return true;
            }
            default: {
                return false;
            }
        }
    }

    private boolean positionSwitcher(int firstPosition, int secondPosition) {
        if (positionSwitcher(secondPosition)) {
            return true;
        } else if (secondPosition == UNSELECT_FIGURE) {
            return false;
        } else {
            Move.clearFigureStates(this.getBoard());
            Move.move(this, firstPosition, secondPosition);
            return false;
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
        invertWhoseTurn();
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
                System.out.print("ENTER A NUMBER [0-63 figure choice / 99 save and exit / 100 exit]: ");
                firstPosition = scanner.nextInt();
                if (!Move.isLegalFirstPosition(this, this.whoseTurn, firstPosition)) {
                    thereAreNoExceptions = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Invalid input data");
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
                System.out.print("ENTER A NUMBER [0-63 figure choice / 99 save and exit / 100 exit / 111 unselect figure]: ");
                secondPosition = scanner.nextInt();
                if (!Move.isLegalSecondPosition(this, this.whoseTurn, secondPosition)) {
                    thereAreNoExceptions = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Invalid input data");
                thereAreNoExceptions = false;
            }
        } while (!thereAreNoExceptions);

        return secondPosition;
    }

    public void invertWhoseTurn() {
        if (whoseTurn == FigureColor.WHITE) {
            whoseTurn = FigureColor.BLACK;
        } else {
            whoseTurn = FigureColor.WHITE;
        }
    }

    public void setWhoseTurn(FigureColor figureColor) {
        this.whoseTurn = figureColor;
    }

    public void setWhoseTurn(String figureColor) {
        if (figureColor.equals(FigureColor.WHITE.toString())) {
            this.whoseTurn = FigureColor.WHITE;
        } else {
            this.whoseTurn = FigureColor.BLACK;
        }
    }

    public void whoWon() {
        if (this.whoseTurn == FigureColor.BLACK) {
            System.out.println(this.getPlayer1().getPlayerName() + " WON!");
        } else {
            System.out.println(this.getPlayer2().getPlayerName() + " WON!");
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

    public FigureColor getWhoseTurn() {
        return whoseTurn;
    }

    public int getId() {
        return id;
    }

}
