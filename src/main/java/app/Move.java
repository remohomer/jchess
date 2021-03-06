package app;

import enums.Error;
import enums.FigureType;
import enums.FigureColor;
import enums.PrintBoardType;
import figures.*;

import java.util.Locale;
import java.util.Scanner;


public class Move {

    private static boolean pawnIsMovedOrFigureIsTaking = false;
    public static int lastMoveDestiny = -1;
    public static int lastMoveSource = -1;

    public static void move(Game game, int firstPosition, int secondPosition) {
        try {
            FileManager.saveLastMoveToFileTxt(game);
            setPawnIsMovedOrFigureIsTaking(game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN
                    || game.getBoard().getField(secondPosition).getFigure().getFigureType() != FigureType.EMPTY);

            if (doCastling(game.getBoard(), firstPosition, secondPosition)) {
                updateCastlingConditions(game.getBoard(), firstPosition);
                setLastMove(firstPosition, secondPosition);
            } else if (doPromotion(game, firstPosition, secondPosition)) {
                game.getBoard().getField(secondPosition).getFigure().setActivePromotion(false);
                setLastMove(firstPosition, secondPosition);
            } else if (doEnpassant(game, firstPosition, secondPosition)) {
                setLastMove(firstPosition, secondPosition);
            } else {
                updateCastlingConditions(game.getBoard(), firstPosition);
                isEnPassantActiveForNextMove(game.getBoard(), firstPosition, secondPosition);

                game.getBoard().getField(secondPosition).setFigure(game.getBoard().getField(firstPosition).getFigure());
                game.getBoard().getField(firstPosition).setFigure(new Empty());
                setLastMove(firstPosition, secondPosition);
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean doCastling(Board board, int firstPosition, int secondPosition) {
        if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING && firstPosition == 60 && secondPosition == 62) {
            doWhiteKingSideCastling(board);
            return true;
        } else if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING && firstPosition == 60 && secondPosition == 58) {
            doWhiteQueenSideCastling(board);
            return true;
        } else if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING && firstPosition == 4 && secondPosition == 6) {
            doBlackKingSideCastling(board);
            return true;
        } else if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING && firstPosition == 4 && secondPosition == 2) {
            doBlackQueenSideCastling(board);
            return true;
        }
        return false;
    }

    public static void doWhiteKingSideCastling(Board board) {
        board.getField(61).setFigure(new Rook(FigureColor.WHITE));
        board.getField(62).setFigure(new King(FigureColor.WHITE));
        board.getField(60).setFigure(new Empty());
        board.getField(63).setFigure(new Empty());
        board.getCastlingConditions().setWhiteKingMove(true);
        board.getCastlingConditions().setWhiteRightRookMove(true);
    }

    public static void doWhiteQueenSideCastling(Board board) {
        if (board.getField(63).getFigure().getFigureColor() == FigureColor.WHITE && board.getField(56).getFigure().getFigureType() == FigureType.ROOK) {
            board.getCastlingConditions().setWhiteKingMove(true);
            board.getCastlingConditions().setWhiteLeftRookMove(true);
            board.getField(59).setFigure(new Rook(FigureColor.WHITE));
            board.getField(58).setFigure(new King(FigureColor.WHITE));
            board.getField(60).setFigure(new Empty());
            board.getField(56).setFigure(new Empty());
        }
    }

    public static void doBlackKingSideCastling(Board board) {
        if (board.getField(7).getFigure().getFigureColor() == FigureColor.BLACK && board.getField(7).getFigure().getFigureType() == FigureType.ROOK) {
            board.getField(5).setFigure(new Rook(FigureColor.BLACK));
            board.getField(6).setFigure(new King(FigureColor.BLACK));
            board.getField(4).setFigure(new Empty());
            board.getField(7).setFigure(new Empty());
            board.getCastlingConditions().setBlackKingMove(true);
            board.getCastlingConditions().setBlackRightRookMove(true);
        }
    }

    public static void doBlackQueenSideCastling(Board board) {
        if (board.getField(0).getFigure().getFigureColor() == FigureColor.BLACK && board.getField(0).getFigure().getFigureType() == FigureType.ROOK) {
            board.getField(3).setFigure(new Rook(FigureColor.BLACK));
            board.getField(2).setFigure(new King(FigureColor.BLACK));
            board.getField(4).setFigure(new Empty());
            board.getField(0).setFigure(new Empty());
            board.getCastlingConditions().setBlackKingMove(true);
            board.getCastlingConditions().setBlackLeftRookMove(true);
        }
    }

    public static boolean doPromotion(Game game, int firstPosition, int secondPosition) {

        if (game.getBoard().getField(firstPosition).getFigure().isPawn(game.getBoard(), firstPosition)) {

            if (game.getBoard().getField(secondPosition).getRow() == 8 || game.getBoard().getField(secondPosition).getRow() == 1) {
                game.getBoard().getField(firstPosition).setFigure(new Empty());

                game.getBoard().getField(secondPosition).getFigure().setActivePromotion(true);
                Printer.printBoard(game, Printer.NOT_SELECTED_FIGURE, PrintBoardType.DEFAULT);

                char typeOfPromotionFigure = (askPlayerForTypeOfPromotionFigure());

                switch (typeOfPromotionFigure) {
                    case 'Q':
                        game.getBoard().getField(secondPosition).setFigure(new Queen(game.getWhoseTurn()));
                        break;
                    case 'R':
                        game.getBoard().getField(secondPosition).setFigure(new Rook(game.getWhoseTurn()));
                        break;
                    case 'B':
                        game.getBoard().getField(secondPosition).setFigure(new Bishop(game.getWhoseTurn()));
                        break;
                    case 'N':
                        game.getBoard().getField(secondPosition).setFigure(new Knight(game.getWhoseTurn()));
                        break;
                    default:
                        break;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean doEnpassant(Game game, int firstPosition, int secondPosition) {
        if (game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN
                && game.getWhoseTurn() == FigureColor.WHITE
                && game.getBoard().getField(secondPosition).getFigure().isEnPassantForWhite()
                && game.getBoard().getField(secondPosition).getFigure().isLegalMove()
                || game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN
                && game.getWhoseTurn() == FigureColor.BLACK
                && game.getBoard().getField(secondPosition).getFigure().isEnPassantForBlack()
                && game.getBoard().getField(secondPosition).getFigure().isLegalMove()
        ) {
            game.getBoard().getField(firstPosition).setFigure(new Empty());
            game.getBoard().getField(secondPosition).setFigure(new Pawn(game.getWhoseTurn()));
            if (game.getWhoseTurn() == FigureColor.WHITE) {
                game.getBoard().getField(secondPosition + figures.Movement.BOTTOM).setFigure(new Empty());
            } else {
                game.getBoard().getField(secondPosition + figures.Movement.TOP).setFigure(new Empty());
            }
                return true;

        }
        return false;
    }

    public static char askPlayerForTypeOfPromotionFigure() {

        char figureType = '0';

        try {
            while (!isCorrectFigureType(figureType)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("What to promote the pawn?? [ Q / R / B / N ]: ");
                figureType = scanner.nextLine().toUpperCase(Locale.ROOT).charAt(0);
            }
        } catch (Exception e) {
            System.out.println(Error.INCORRECT_INPUT_DATA.getMessage());
        }

        return figureType;
    }

    public static boolean isCorrectFigureType(char figureType) {
        switch (figureType) {
            case 'Q':
            case 'R':
            case 'B':
            case 'N':
                return true;
            default:
                return false;
        }
    }

    public static void isEnPassantActiveForNextMove(Board board, int firstPosition, int secondPosition) {
        if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN && board.getField(firstPosition).getRow() == 2 && board.getField(secondPosition).getRow() == 4) {
            board.getField(firstPosition + figures.Movement.TOP).getFigure().setEnPassantForBlack(true);
            System.out.println("setEnPassantForBlack(true)");
        } else if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN && board.getField(firstPosition).getRow() == 7 && board.getField(secondPosition).getRow() == 5) {
            board.getField(firstPosition + figures.Movement.BOTTOM).getFigure().setEnPassantForWhite(true);
            System.out.println("setEnPassantForWhite(true)");
        }
    }

    public static void updateCastlingConditions(Board board, int firstPosition) {

        board.getCastlingConditions().setWhiteKingMove(isWhiteKingMoved(board, firstPosition));
        board.getCastlingConditions().setWhiteLeftRookMove(isWhiteLeftRookMoved(board, firstPosition));
        board.getCastlingConditions().setWhiteRightRookMove(isWhiteRightRookMoved(board, firstPosition));

        board.getCastlingConditions().setBlackKingMove(isBlackKingMoved(board, firstPosition));
        board.getCastlingConditions().setBlackLeftRookMove(isBlackLeftRookMoved(board, firstPosition));
        board.getCastlingConditions().setBlackRightRookMove(isBlackRightRookMoved(board, firstPosition));
    }

    public static boolean isWhiteKingMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isWhiteKingMove())
            return true;
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 60;
    }

    public static boolean isWhiteLeftRookMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isWhiteLeftRookMove())
            return true;
        return (board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 56);
    }

    public static boolean isWhiteRightRookMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isWhiteRightRookMove())
            return true;
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 63;
    }

    public static boolean isBlackKingMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isBlackKingMove())
            return true;
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 4;
    }

    public static boolean isBlackLeftRookMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isBlackLeftRookMove())
            return true;
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 0;
    }

    public static boolean isBlackRightRookMoved(Board board, int firstPosition) {
        if (board.getCastlingConditions().isBlackRightRookMove())
            return true;
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 7;
    }

    public static FigureColor invertColor(FigureColor figureColor) {
        if (figureColor == FigureColor.WHITE) {
            return FigureColor.BLACK;
        } else if (figureColor == FigureColor.BLACK) {
            return FigureColor.WHITE;
        } else {
            return FigureColor.NONE;
        }
    }

    public static int[] findNonEmptyFiguresPosition(Game game, boolean invertColor) {
        int[] tFiguresPosition = new int[64];
        int nr = 0;

        FigureColor watchingFigureColor = game.getWhoseTurn();
        if (invertColor) {
            watchingFigureColor = invertColor(watchingFigureColor);
        }

        for (Field field : game.getBoard().getWholeField()) {
            if (field.getFigure().getFigureColor() == watchingFigureColor) {
                tFiguresPosition[nr] = field.getNumber();
                nr++;
            }
        }

        int[] figuresPosition = new int[nr];
        System.arraycopy(tFiguresPosition, 0, figuresPosition, 0, figuresPosition.length);

        return figuresPosition;
    }

    public static void setUnderPressureandProtected(Game game) {

        final int[] figuresPosition = findNonEmptyFiguresPosition(game, true);

        game.invertWhoseTurn();
        for (int position : figuresPosition) {
            game.getBoard().getField(position).getFigure().setLegalMovement(game, position);
        }
        game.invertWhoseTurn();

        clearSelectedFigureAndLegalMoves(game.getBoard());
    }

    public static void findCheckMateOrDraw(Game game) {

        final int[] figuresPosition = findNonEmptyFiguresPosition(game, false);

        for (int position : figuresPosition) {
            game.getBoard().getField(position).getFigure().setLegalMovement(game, position);

        }
        isCheckMateOrDraw(game);
        clearSelectedFigureAndLegalMoves(game.getBoard());

    }

    public static void isCheckMateOrDraw(Game game) {

        boolean isLegalMoveHere = false;
        for (Field field : game.getBoard().getWholeField()) {
            if (field.getFigure().isLegalMove()) {
                isLegalMoveHere = true;
                break;
            }
        }
        if (!isLegalMoveHere) {
            clearSelectedFigureAndLegalMoves(game.getBoard());
            Printer.printBoard(game, Printer.NOT_SELECTED_FIGURE, PrintBoardType.DEFAULT);
            if (isKingCheck(game.getBoard())) {
                game.setCheckMate(true);
                System.out.println();
                System.out.println("CHECK MATE!");
                game.whoWon();
            } else {
                game.setDraw(true);
                System.out.println();
                System.out.println("IS A DRAW!");
            }
        }
    }

    public static boolean isKingCheck(Board board) {

        boolean check = false;

        for (Field field : board.getWholeField()) {

            if (field.getFigure().getFigureColor() == FigureColor.WHITE) {
                if (field.getFigure().getFigureType() == FigureType.KING && field.getFigure().isUnderPressureByBlack()) {
                    board.getCastlingConditions().setWhiteKingCheck(true);
                    check = true;
                }
            } else if (field.getFigure().getFigureColor() == FigureColor.BLACK) {
                if (field.getFigure().getFigureType() == FigureType.KING && field.getFigure().isUnderPressureByWhite()) {
                    board.getCastlingConditions().setBlackKingCheck(true);
                    check = true;
                }
            }
        }

        if (check) {
            return true;
        }
        board.getCastlingConditions().setWhiteKingCheck(false);
        board.getCastlingConditions().setBlackKingCheck(false);
        return false;
    }

    public static boolean isLegalFirstPosition(Game game, FigureColor whichPlayer, int firstPosition) {
        if (firstPosition == Game.EXIT_GAME || firstPosition == Game.SAVE_AND_EXIT_GAME) {
            return true;
        }
        if (game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.EMPTY) {
            System.out.println("The field you selected is empty");
            return false;
        }
        if (whichPlayer != game.getBoard().getField(firstPosition).getFigure().getFigureColor()) {
            System.out.println("Its not your figure");
            return false;
        }
        return true;
    }

    public static boolean isLegalSecondPosition(Game game, FigureColor whichPlayer, int secondPosition) {
        if (secondPosition == Game.UNSELECT_FIGURE || secondPosition == Game.EXIT_GAME || secondPosition == Game.SAVE_AND_EXIT_GAME) {
            return true;
        }
        if (whichPlayer == game.getBoard().getField(secondPosition).getFigure().getFigureColor()) {
            System.out.println("You cannot take your own pieces");
            return false;
        }
        if (!game.getBoard().getField(secondPosition).getFigure().isLegalMove()) {
            System.out.println("Its not legal move");
            return false;
        }
        return true;
    }

    public static void clearEnPassant(Game game) {
        for (Field field : game.getBoard().getWholeField()) {
            if (game.getWhoseTurn() == FigureColor.WHITE) {
                field.getFigure().setEnPassantForBlack(false);
            } else if (game.getWhoseTurn() == FigureColor.BLACK) {
                field.getFigure().setEnPassantForWhite(false);
            }

        }
    }

    public static void clearSelectedFigureAndLegalMoves(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setSelected(false);
            field.getFigure().setLegalMove(false);
        }
    }

    public static void clearUnderPressure(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setUnderPressureByWhite(false);
            field.getFigure().setUnderPressureByBlack(false);
        }
    }

    public static void clearProtected(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setProtectedByWhite(false);
            field.getFigure().setProtectedByBlack(false);
        }
    }

    public static void clearCheckLine(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setCheckLine(false);
        }
    }

    public static void clearPinnedCheckLine(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setPinnedCheckLine(false);
        }
    }

    public static void clearPinned(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setPinned(false);
        }
    }

    public static void clearFigureStates(Game game) {
        clearEnPassant(game);
        Move.clearUnderPressure(game.getBoard());
        Move.clearProtected(game.getBoard());
        Move.clearCheckLine(game.getBoard());   // Usuwa sie przy ponownym wybieraniu figury
        Move.clearPinned(game.getBoard());      // Usuwa sie przy ponownym wybieraniu figury
        clearPinnedCheckLine(game.getBoard());  // Usuwa sie przy ponownym wybieraniu figury
    }

    public static boolean getPawnIsMovedOrFigureIsTaking() {
        return pawnIsMovedOrFigureIsTaking;
    }

    public static void setPawnIsMovedOrFigureIsTaking(boolean aaa) {
        pawnIsMovedOrFigureIsTaking = aaa;
    }

    public void execute(Game game) {

    }

    static int getLastMoveSource() {
        return lastMoveSource;
    }

    static int getLastMoveDestiny() {
        return lastMoveDestiny;
    }

    static void setLastMove(int firstPosition, int secondPosition) {
        lastMoveDestiny = firstPosition;
        lastMoveSource = secondPosition;
    }

}
