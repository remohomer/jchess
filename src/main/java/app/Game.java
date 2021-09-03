package app;

import booleans.CastlingConditions;
import enums.FigureColor;
import booleans.GameStatus;
import enums.Error;
import gui.Table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import static app.FileManager.initializeFigure;


public class Game extends GameStatus {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private FigureColor whoseTurn;
    public int sourcePosition = -1;
    public int destinyPosition = -1;
    private static int counter = 1;

    protected final int ID;

    public final static int SAVE_AND_EXIT_GAME = 99;
    public final static int EXIT_GAME = 100;
    public final static int UNSELECT_FIGURE = 111;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.whoseTurn = FigureColor.WHITE;
        this.player1 = player1;
        this.player2 = player2;
        ID = counter++;
    }

    public Game(Game game) {
        this.board = game.getBoard();
        this.whoseTurn = game.getWhoseTurn();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.ID = game.getId();
    }

    public void guiGamePlay() {
        setLegalMovesForCurrentPlayer();
        Table table = new Table(this);
    }

    public void consoleGamePlay() {
        this.setActiveGame(true);
        while (this.isActiveGame()) {
            if (lookingForDrawBy50PassiveMoves()) {
                break;
            }
            setLegalMovesForCurrentPlayer();

            loadPositions:
            {
                do {
                    Printer.printAllOfBoards(this,Printer.NOT_SELECTED_FIGURE);
                    Printer.printBooleans(this);

                    sourcePosition = loadFirstPosition();
                    if (IsExitGame(sourcePosition)) {
                        break loadPositions;
                    }
                    Printer.printAllOfBoards(this,sourcePosition);
                    Printer.printBooleans(this);

                    destinyPosition = loadSecondPosition();
                    if (IsExitGame(sourcePosition, destinyPosition)) {
                        break loadPositions;
                    }
                } while (destinyPosition == UNSELECT_FIGURE);
            }

            if (this.isActiveGame()) {
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

    private boolean IsExitGame(int position) {
        switch (position) {
            case UNSELECT_FIGURE: {
                Move.clearSelectedFigureAndLegalMoves(this.getBoard());
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

    private boolean IsExitGame(int firstPosition, int secondPosition) {
        if (IsExitGame(secondPosition)) {
            return true;
        } else if (secondPosition == UNSELECT_FIGURE) {
            return false;
        } else {
            Move.clearFigureStates(this);
            Move.move(this, firstPosition, secondPosition);
            return false;
        }
    }

    private boolean lookingForDrawBy50PassiveMoves() {
        FileManager.saveCurrentBoard(this, true);
        FileManager.scanCurrentBoard(this);
        return this.isDraw();
    }

    public void setLegalMovesForCurrentPlayer() {
        Move.setUnderPressureandProtected(this);
        Move.isKingCheck(this.board);
    }

    private void exitGameConditions() {
        this.setActiveGame(false);
        FileManager.deleteCurrentBoardFile(this);
    }

    public boolean lookingForCheckMateOrDraw() {
        invertWhoseTurn();
        Move.setUnderPressureandProtected(this);
        Move.isKingCheck(this.board);
        Move.findCheckMateOrDraw(this);
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
                System.out.println(Error.INCORRECT_INPUT_DATA.getMessage());
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
                System.out.println(Error.INCORRECT_INPUT_DATA.getMessage());
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
        return ID;
    }

    public void loadGame(int gameId) {
        Game game = new Game(InitializeGame.newStandardGame());
        try {
            String patch = FileManager.patchBuilder(gameId, "game", "txt");
            BufferedReader reader = new BufferedReader(new FileReader(patch));

            game.getPlayer1().setPlayerName(reader.readLine());
            game.getPlayer2().setPlayerName(reader.readLine());
            game.setWhoseTurn(reader.readLine());

            for (int i = 0; i < 64; i++) {
                StringTokenizer token = new StringTokenizer(reader.readLine(), "|");
                int position = Integer.parseInt(token.nextToken());

                String figureType = token.nextToken();
                String figureColor = token.nextToken();
                initializeFigure(game.getBoard(), figureColor, figureType, position);
                game.setLegalMovesForCurrentPlayer();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.sourcePosition = game.sourcePosition;
        this.destinyPosition = game.sourcePosition;
        this.setWhoseTurn(game.whoseTurn);
        this.setActiveGame(game.isActiveGame());
        this.setDraw(game.isDraw());
        this.setCheckMate(game.isCheckMate());

        this.getPlayer1().setPlayerName(game.getPlayer1().getPlayerName());
        this.getPlayer2().setPlayerName(game.getPlayer2().getPlayerName());
        this.getBoard().initializeLoadedBoard(game);
        this.getBoard().setCastlingConditions(game.getBoard().getCastlingConditions());
    }

    public void newGame() {
        this.sourcePosition = -1;
        this.destinyPosition = -1;
        this.setWhoseTurn(FigureColor.WHITE);
        this.setActiveGame(true);
        this.setDraw(false);
        this.setCheckMate(false);
        this.getPlayer1().setPlayerName("Player_1");
        this.getPlayer2().setPlayerName("Player_2");
        this.getBoard().initializeBoard();
        this.getBoard().setCastlingConditions(new CastlingConditions());
    }

}
