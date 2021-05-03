package app;

import enums.PrintBoardType;
import enums.FigureColor;


public class Printer {

    public static final int NOT_SELECTED_FIGURE = -1;

    public static void printBoard(Game game, PrintBoardType printBoardType, int selectedFigurePosition) {

        if (selectedFigurePosition != -1) {
            game.getBoard().getField(selectedFigurePosition).getFigure().movement(game, selectedFigurePosition);
        }
        System.out.println();

        String player = game.getWhoseTurn() == FigureColor.BLACK ? ("\t** " + game.getPlayer2().getPlayerName() + "**") : ("\t" + game.getPlayer2().getPlayerName());

        if (printBoardType == PrintBoardType.DEFAULT) {
            System.out.println(player);
        } else {
            System.out.println(printBoardType.toString());
        }

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }

            System.out.print(boardSwitch(game, i, printBoardType));

            if ((i + 1) % 8 == 0) {
                System.out.print("   " + row + "\n");
            }

            if (i != 0 && (i + 1) % 8 == 0) {
                row--;
            }

            if (i == 63) {
                System.out.println("\n      a  b  c  d  e  f  g  h  ");
            }
        }
        player = game.getWhoseTurn() == FigureColor.WHITE ? ("\t\t\t\t\t\t** " + game.getPlayer1().getPlayerName() + "**") : ("\t\t\t\t\t\t" + game.getPlayer1().getPlayerName());
        if (printBoardType == PrintBoardType.DEFAULT) {
            System.out.println(player);
        }
    }

    public static String boardSwitch(Game game, int i, PrintBoardType printBoardType) {

        switch (printBoardType.toString()) {
            case "DEFAULT": {
                return defaultBoardConditions(game, i);
            }
            case "NUMBERS": {
                return i < 10 ? (" " + i + " ") : (" " + i);
            }
            case "LEGAL_MOVES": {
                return game.getBoard().getField(i).getFigure().isLegalMove() ? (" Lm") : ("   ");
            }
            case "UNDER_PRESSURE": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return game.getBoard().getField(i).getFigure().isUnderPressureByBlack() ? ("Up_") : ("   ");
                } else {
                    return game.getBoard().getField(i).getFigure().isUnderPressureByWhite() ? ("Up ") : ("   ");
                }
            }
            case "PROTECTED": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return game.getBoard().getField(i).getFigure().isProtectedByBlack() ? (" Pr") : ("   ");
                } else {
                    return game.getBoard().getField(i).getFigure().isProtectedByWhite() ? (" Pr") : ("   ");
                }
            }
            case "EN_PASSANT": {
                return game.getBoard().getField(i).getFigure().isEnPassant() ? (" Ep") : ("   ");
            }
            case "CHECK_LINES": {
                return game.getBoard().getField(i).getFigure().isCheckLine() ? (" Cl") : ("   ");
            }
            case "PINNED_AND_PINNED_CHECK_LINES": {
                if (game.getBoard().getField(i).getFigure().isPinned()) {
                    return (" P ");
                } else if (game.getBoard().getField(i).getFigure().isPinnedCheckLine()) {
                    return (" l ");
                } else {
                    return ("   ");
                }
            }
            default: {
                return "ERROR: reading data from enums.PrintBoardType";
            }
        }
    }

    public static String defaultBoardConditions(Game game, int i) {

        String fColor = game.getBoard().getField(i).getFigure().getFigureColor() == FigureColor.BLACK ? "_" : " ";
        String selectedOrLegal = " ";
        if (game.getBoard().getField(i).getFigure().isSelected()) {
            selectedOrLegal = "@";
        } else if (game.getBoard().getField(i).getFigure().isLegalMove()) {
            selectedOrLegal = "*";
        }

        switch (game.getBoard().getField(i).getFigure().getFigureType().toString()) {
            case "EMPTY": {
                return game.getBoard().getField(i).getFigure().isActivePromotion() ? (" ? ") : (" " + selectedOrLegal + " ");
            }
            case "PAWN": {
                return fColor + "P" + selectedOrLegal;
            }
            case "KNIGHT": {
                return fColor + "N" + selectedOrLegal;
            }
            case "BISHOP": {
                return fColor + "B" + selectedOrLegal;
            }
            case "ROOK": {
                return fColor + "R" + selectedOrLegal;
            }
            case "QUEEN": {
                return fColor + "Q" + selectedOrLegal;
            }
            case "KING": {
                return fColor + "K" + selectedOrLegal;
            }
            default: {
                return "ERROR: reading data from enums.FigureType";
            }
        }
    }

    public static void printFigureStates(Game game) {
        for (int i = 0; i < 64; i++) {
            if (i < 10) System.out.print("0" + game.getBoard().getField(i).getNumber());
            else System.out.print(game.getBoard().getField(i).getNumber());
            System.out.print(" Select: " + game.getBoard().getField(i).getFigure().isSelected());
            System.out.print(" | Legal: " + game.getBoard().getField(i).getFigure().isLegalMove());
            System.out.print(" | EnPassant: " + game.getBoard().getField(i).getFigure().isEnPassant());
            System.out.print(" | Pin: " + game.getBoard().getField(i).getFigure().isPinned());
            System.out.print(" | PinCheckLn: " + game.getBoard().getField(i).getFigure().isPinnedCheckLine());
            System.out.print(" | CheckLn: " + game.getBoard().getField(i).getFigure().isCheckLine());
            System.out.print(" | PressByWhite: " + game.getBoard().getField(i).getFigure().isUnderPressureByWhite());
            System.out.print(" | ProtectByWhite: " + game.getBoard().getField(i).getFigure().isProtectedByWhite());
            System.out.print(" | PressByBlack: " + game.getBoard().getField(i).getFigure().isUnderPressureByBlack());
            System.out.print(" | ProtectByBlack: " + game.getBoard().getField(i).getFigure().isProtectedByBlack());
            System.out.println(" | Figure: " + game.getBoard().getField(i).getFigure().getFigureType());
        }
    }

    public static void printCastlingBooleans(Game game) {
        System.out.println(" isBlackKingMove(): " + game.getBoard().getCastlingConditions().isBlackKingMove());
        System.out.println(" isWhiteKingMove(): " + game.getBoard().getCastlingConditions().isWhiteKingMove());
        System.out.println(" isWhiteKingChecking(): " + game.getBoard().getCastlingConditions().isWhiteKingCheck());
        System.out.println(" isBlackKingChecking(): " + game.getBoard().getCastlingConditions().isBlackKingCheck());
        System.out.println(" isWhiteLeftRookMove(): " + game.getBoard().getCastlingConditions().isWhiteLeftRookMove());
        System.out.println(" isBlackLeftRookMove(): " + game.getBoard().getCastlingConditions().isBlackLeftRookMove());
        System.out.println(" isWhiteRightRookMove(: " + game.getBoard().getCastlingConditions().isWhiteRightRookMove());
        System.out.println(" isBlackRightRookMove(): " + game.getBoard().getCastlingConditions().isBlackRightRookMove());
    }
}


