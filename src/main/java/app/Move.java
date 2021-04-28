package app;

import enums.FigureType;
import enums.FigureColor;
import figures.*;

import java.util.Locale;
import java.util.Scanner;


public class Move implements figures.Movement {

    private static boolean pawnIsMovedOrFigureIsTaking = false;

    public static void move(Game game, int firstPosition, int secondPosition) {
        try {

            if (game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN
                    || game.getBoard().getField(secondPosition).getFigure().getFigureType() != FigureType.EMPTY)
                pawnIsMovedOrFigureIsTaking = true;
            else
                pawnIsMovedOrFigureIsTaking = false;

            if (doEnpassant(game, firstPosition, secondPosition)) ;
            else if (doCastling(game.getBoard(), firstPosition, secondPosition)) ;
            else if (doPromotion(game, firstPosition, secondPosition)) ;
            else {
                updateEnPassantConditions(game.getBoard(), firstPosition, secondPosition);
                updateCastlingConditions(game.getBoard(), firstPosition);

                game.getBoard().getField(secondPosition).setFigure(game.getBoard().getField(firstPosition).getFigure());
                game.getBoard().getField(firstPosition).setFigure(new Empty());
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean doEnpassant(Game game, int firstPosition, int secondPosition) {
        if (game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN
                && game.getBoard().getField(secondPosition).getFigure().isEnPassant()
                && game.getBoard().getField(secondPosition).getFigure().isLegalMove()) {
            game.getBoard().getField(firstPosition).setFigure(new Empty());
            game.getBoard().getField(secondPosition).setFigure(new Pawn(game.getWhichPlayer()));
            if (game.getWhichPlayer() == FigureColor.WHITE)
                game.getBoard().getField(secondPosition + BOTTOM).setFigure(new Empty());
            else
                game.getBoard().getField(secondPosition + TOP).setFigure(new Empty());
            return true;
        }
        clearEnPassant(game.getBoard());
        return false;
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
        board.getCastlingConditions().setWhiteKingMove(true);
        board.getCastlingConditions().setWhiteLeftRookMove(true);
        board.getField(59).setFigure(new Rook(FigureColor.WHITE));
        board.getField(58).setFigure(new King(FigureColor.WHITE));
        board.getField(60).setFigure(new Empty());
        board.getField(56).setFigure(new Empty());
    }

    public static void doBlackKingSideCastling(Board board) {
        board.getField(5).setFigure(new Rook(FigureColor.BLACK));
        board.getField(6).setFigure(new King(FigureColor.BLACK));
        board.getField(4).setFigure(new Empty());
        board.getField(7).setFigure(new Empty());
        board.getCastlingConditions().setBlackKingMove(true);
        board.getCastlingConditions().setBlackRightRookMove(true);
    }

    public static void doBlackQueenSideCastling(Board board) {
        board.getField(3).setFigure(new Rook(FigureColor.BLACK));
        board.getField(2).setFigure(new King(FigureColor.BLACK));
        board.getField(4).setFigure(new Empty());
        board.getField(0).setFigure(new Empty());
        board.getCastlingConditions().setBlackKingMove(true);
        board.getCastlingConditions().setBlackLeftRookMove(true);
    }

    public static boolean doPromotion(Game game, int firstPosition, int secondPosition) {

        if (game.getBoard().getField(firstPosition).getFigure().isPawn(game.getBoard(), firstPosition)) {

            if (game.getBoard().getField(secondPosition).getRow() == 8) {
                game.getBoard().getField(firstPosition).setFigure(new Empty());

                char typeOfPromotionFigure = (askPlayerForTypeOfPromotionFigure());

                switch (typeOfPromotionFigure) {
                    case 'Q':
                        game.getBoard().getField(secondPosition).setFigure(new Queen(FigureColor.WHITE));
                        break;
                    case 'R':
                        game.getBoard().getField(secondPosition).setFigure(new Rook(FigureColor.WHITE));
                        break;
                    case 'B':
                        game.getBoard().getField(secondPosition).setFigure(new Bishop(FigureColor.WHITE));
                        break;
                    case 'N':
                        game.getBoard().getField(secondPosition).setFigure(new Knight(FigureColor.WHITE));
                        break;
                    default:
                        break;
                }
                return true;

            } else if (game.getBoard().getField(secondPosition).getRow() == 1) {
                game.getBoard().getField(firstPosition).setFigure(new Empty());

                char typeOfPromotionFigure = (askPlayerForTypeOfPromotionFigure());

                switch (typeOfPromotionFigure) {
                    case 'Q':
                        game.getBoard().getField(secondPosition).setFigure(new Queen(FigureColor.BLACK));
                        break;
                    case 'R':
                        game.getBoard().getField(secondPosition).setFigure(new Rook(FigureColor.BLACK));
                        break;
                    case 'B':
                        game.getBoard().getField(secondPosition).setFigure(new Bishop(FigureColor.BLACK));
                        break;
                    case 'N':
                        game.getBoard().getField(secondPosition).setFigure(new Knight(FigureColor.BLACK));
                        break;
                    default:
                        break;
                }
                return true;
            }
        }
        return false;
    }

    public static char askPlayerForTypeOfPromotionFigure() {

        char figureType = '0';

        try {
            while (!isCorrectFigureType(figureType)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Na jaką figurę promować pionka? [ Q / R / B / N ]: ");
                figureType = scanner.nextLine().toUpperCase(Locale.ROOT).charAt(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR: Niepoprawna nazwa figury.");
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

    public static void updateEnPassantConditions(Board board, int firstPosition, int secondPosition) {
        if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN && board.getField(firstPosition).getRow() == 2 && board.getField(secondPosition).getRow() == 4) {
            board.getField(firstPosition + TOP).getFigure().setEnPassant(true);
        } else if (board.getField(firstPosition).getFigure().getFigureType() == FigureType.PAWN && board.getField(firstPosition).getRow() == 7 && board.getField(secondPosition).getRow() == 5) {
            board.getField(firstPosition + BOTTOM).getFigure().setEnPassant(true);
        }
    }

    public static void updateCastlingConditions(Board board, int firstPosition) {

        if (DoIMoveWhiteKing(board, firstPosition))
            board.getCastlingConditions().setWhiteKingMove(true);
        if (DoIMoveWhiteLeftRook(board, firstPosition))
            board.getCastlingConditions().setWhiteLeftRookMove(true);
        if (DoIMoveWhiteRightRook(board, firstPosition))
            board.getCastlingConditions().setWhiteRightRookMove(true);
        if (DoIMoveBlackKing(board, firstPosition))
            board.getCastlingConditions().setBlackKingMove(true);
        if (DoIMoveBlackeLeftRook(board, firstPosition))
            board.getCastlingConditions().setBlackLeftRookMove(true);
        if (DoIMoveBlackRightRook(board, firstPosition))
            board.getCastlingConditions().setBlackRightRookMove(true);
    }

    public static boolean DoIMoveWhiteKing(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 60;
    }

    public static boolean DoIMoveWhiteLeftRook(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 56;
    }

    public static boolean DoIMoveWhiteRightRook(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.WHITE
                && board.getField(firstPosition).getNumber() == 63;
    }

    public static boolean DoIMoveBlackKing(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.KING
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 4;
    }

    public static boolean DoIMoveBlackeLeftRook(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 0;
    }

    public static boolean DoIMoveBlackRightRook(Board board, int firstPosition) {
        return board.getField(firstPosition).getFigure().getFigureType() == FigureType.ROOK
                && board.getField(firstPosition).getFigure().getFigureColor() == FigureColor.BLACK
                && board.getField(firstPosition).getNumber() == 7;
    }

    public static int[] findAllPositionsOfNoNEmptyFiguresOnTheBoard(Game game, boolean inverted) {
        if (inverted) {
            int[] tfiguresPosition = new int[64];
            int nr = 0;

            if (game.getWhichPlayer() == FigureColor.WHITE) {

                for (Field field : game.getBoard().getWholeField()) {
                    if (field.getFigure().getFigureColor() == FigureColor.BLACK) {
                        tfiguresPosition[nr] = field.getNumber();
                        nr++;
                    }
                }

            } else if (game.getWhichPlayer() == FigureColor.BLACK) {

                for (Field field : game.getBoard().getWholeField()) {
                    if (field.getFigure().getFigureColor() == FigureColor.WHITE) {
                        tfiguresPosition[nr] = field.getNumber();
                        nr++;
                    }
                }

            }
            int[] figuresPosition = new int[nr];

            for (int i = 0; i < figuresPosition.length; i++) {
                figuresPosition[i] = tfiguresPosition[i];
            }
            return figuresPosition;
        } else {
            int[] tfiguresPosition = new int[64];
            int nr = 0;

            if (game.getWhichPlayer() == FigureColor.WHITE) {

                for (Field field : game.getBoard().getWholeField()) {
                    if (field.getFigure().getFigureColor() == FigureColor.WHITE) {
                        tfiguresPosition[nr] = field.getNumber();
                        nr++;
                    }
                }

            } else if (game.getWhichPlayer() == FigureColor.BLACK) {

                for (Field field : game.getBoard().getWholeField()) {
                    if (field.getFigure().getFigureColor() == FigureColor.BLACK) {
                        tfiguresPosition[nr] = field.getNumber();
                        nr++;
                    }
                }

            }
            int[] figuresPosition = new int[nr];

            for (int i = 0; i < figuresPosition.length; i++) {
                figuresPosition[i] = tfiguresPosition[i];
            }
            return figuresPosition;
        }
    }

    public static void scanBoardAndSetUnderPressureAndProtectedStates(Game game) {

        final int[] figuresPosition = findAllPositionsOfNoNEmptyFiguresOnTheBoard(game, true);

        game.invertWhichPlayer();
        for (int position : figuresPosition) {
            game.getBoard().getField(position).getFigure().movement(game, position);
        }
        game.invertWhichPlayer();

        clearSelectedFigureAndLegalMoves(game.getBoard());
    }

    public static void scanBoardAndFindCheckMateOrDraw(Game game) {

        final int[] figuresPosition = findAllPositionsOfNoNEmptyFiguresOnTheBoard(game, false);

        for (int position : figuresPosition) {
            game.getBoard().getField(position).getFigure().movement(game, position);

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
            Printer.printBoard(game);
            if (isKingCheck(game.getBoard())) {
                game.setCheckMate(true);
                System.out.println();
                System.out.println("SZACH MAT!");
                game.whoWon();
            } else {
                game.setDraw(true);
                System.out.println();
                System.out.println("REMIS!");
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

        if (check)
            return true;
        board.getCastlingConditions().setWhiteKingCheck(false);
        board.getCastlingConditions().setBlackKingCheck(false);
        return false;
    }

    public static boolean isLegalFirstPosition(Game game, FigureColor whichPlayer, int firstPosition) {
        if (firstPosition == Game.SAVE_AND_EXIT_GAME || firstPosition == Game.EXIT_GAME) {
            return true;
        }
        if (game.getBoard().getField(firstPosition).getFigure().getFigureType() == FigureType.EMPTY) {
            System.out.println("ERROR: Pole, które wybrałeś jest puste");
            return false;
        }
        if (whichPlayer != game.getBoard().getField(firstPosition).getFigure().getFigureColor()) {
            System.out.println("ERROR: To nie jest Twoja figura");
            return false;
        }
        return true;
    }

    public static boolean isLegalSecondPosition(Game game, FigureColor whichPlayer, int secondPosition) {
        if (secondPosition == Game.RETURN || secondPosition == Game.SAVE_AND_EXIT_GAME || secondPosition == Game.EXIT_GAME) {
            return true;
        }
        if (whichPlayer == game.getBoard().getField(secondPosition).getFigure().getFigureColor()) {
            System.out.println("ERROR: Nie możesz zbić własnej figury");
            return false;
        }
        if (!game.getBoard().getField(secondPosition).getFigure().isLegalMove()) {
            System.out.println("ERROR: Niepoprawny ruch");
            return false;
        }
        return true;
    }

    public static void clearEnPassant(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setEnPassant(false);
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
            field.getFigure().setPinnedCheckLine(false);
        }
    }

    public static void clearPinned(Board board) {
        for (Field field : board.getWholeField()) {
            field.getFigure().setPinned(false);
        }
    }

    public static void clearFigureStates(Board board) {
        Move.clearUnderPressure(board);
        Move.clearProtected(board);
        Move.clearCheckLine(board);
        Move.clearPinned(board);
    }

    @Override
    public void movement(Game game, int selectedFigurePosition) {
    }

    public static boolean getPawnIsMovedOrFigureIsTaking() {
        return pawnIsMovedOrFigureIsTaking;
    }
}
