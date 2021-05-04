package app;

import enums.FigureColor;
import enums.PrintBoardType;

public class Printer implements ConsoleColors {

    public static final String BG_WHOSE_TURN = BLUE_BACKGROUND_BRIGHT;

    public static final String BG_COLOR_1 = BLUE_BACKGROUND;
    public static final String BG_COLOR_2 = CYAN_BACKGROUND_BRIGHT;
    public static final String BG_SELECTED_COLOR = GREEN_BACKGROUND;

    public static final String BG_LEGAL_MOVE = YELLOW_BACKGROUND;
    public static final String BG_LEGAL_OFFENSIVE_MOVE = RED_BACKGROUND;

    public static final String WHITE_FIGURE = WHITE_BOLD_BRIGHT;
    public static final String BLACK_FIGURE = BLACK_BOLD;
    public static final String EMPTY_FIGURE = WHITE_BOLD;

    public static final int NOT_SELECTED_FIGURE = -1;

    public static void printBoard(Game game, int selectedFigurePosition, PrintBoardType secondBoardType) {

        if (selectedFigurePosition != -1) {
            game.getBoard().getField(selectedFigurePosition).getFigure().movement(game, selectedFigurePosition);
        }
        System.out.println();

        String player = game.getWhoseTurn() == FigureColor.BLACK ?
                (BG_WHOSE_TURN + WHITE_FIGURE + " " + game.getPlayer2().getPlayerName() + " " + RESET) :
                ("\t" + WHITE_FIGURE + game.getPlayer2().getPlayerName() + RESET);

        if (secondBoardType == PrintBoardType.DEFAULT) {
            System.out.println("\t" + player);
        } else {
            System.out.println(player + "\t\t\t\t\t\t\t\t\t" + secondBoardType.toString());
        }

        int row = 8;

        for (Field field : game.getBoard().getWholeField()) {
            if (field.getNumber() == 0) {
                System.out.println("    a  b  c  d  e  f  g  h         |         a  b  c  d  e  f  g  h");
            }

            if (field.getNumber() % 8 == 0) {
                System.out.print(" " + row + " ");
            }

            System.out.print(boardSwitch(game, field.getNumber(), PrintBoardType.DEFAULT));

            if ((field.getNumber() + 1) % 8 == 0) {
                System.out.print(" " + row);

                for (int secondBoardNumber = field.getNumber() - 7; secondBoardNumber <= field.getNumber(); secondBoardNumber++) {
                    if (secondBoardNumber % 8 == 0) {
                        System.out.print("      |      " + row + " ");
                    }
                    System.out.print(boardSwitch(game, secondBoardNumber, secondBoardType));

                    if ((secondBoardNumber + 1) % 8 == 0) {
                        System.out.print(" " + row + "\n");
                    }
                }
            }

            if (field.getNumber() != 0 && (field.getNumber() + 1) % 8 == 0) {
                row--;
            }

            if (field.getNumber() == 63) {
                System.out.println("      a  b  c  d  e  f  g  h       |         a  b  c  d  e  f  g  h");
            }
        }
        player = game.getWhoseTurn() == FigureColor.WHITE ?
                ("\t\t\t\t\t" + BG_WHOSE_TURN + WHITE_FIGURE + " " + game.getPlayer1().getPlayerName() + " " + RESET) :
                ("\t\t\t\t\t" + WHITE_FIGURE + game.getPlayer1().getPlayerName() + RESET);
        System.out.println(player);
    }

    public static String boardSwitch(Game game, int i, PrintBoardType printBoardType) {

        String bgColor = game.getBoard().getField(i).getRow() % 2 == 0 ?
                (i % 2 == 0 ? BG_COLOR_1 : BG_COLOR_2) :
                (i % 2 == 0 ? BG_COLOR_2 : BG_COLOR_1);

        switch (printBoardType.toString()) {
            case "DEFAULT": {
                return defaultBoardConditions(game, i, bgColor);
            }
            case "NUMBERS": {

                return numberBoardConditions(game, i, bgColor);
            }
            case "LEGAL_MOVES": {
                return game.getBoard().getField(i).getFigure().isLegalMove() ? (bgColor + BLACK_FIGURE + " * " + RESET) : (bgColor + "   " + RESET);
            }
            case "UNDER_PRESSURE": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return game.getBoard().getField(i).getFigure().isUnderPressureByBlack() ? (bgColor + BLACK_FIGURE + " * " + RESET) : (bgColor + "   " + RESET);
                } else {
                    return game.getBoard().getField(i).getFigure().isUnderPressureByWhite() ? (bgColor + WHITE_FIGURE + " * " + RESET) : (bgColor + "   " + RESET);
                }
            }
            case "PROTECTED": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return game.getBoard().getField(i).getFigure().isProtectedByBlack() ? (bgColor + BLACK_FIGURE + " @ " + RESET) : (bgColor + "   " + RESET);
                } else {
                    return game.getBoard().getField(i).getFigure().isProtectedByWhite() ? (bgColor + WHITE_FIGURE + " @ " + RESET) : (bgColor + "   " + RESET);
                }
            }
            case "EN_PASSANT": {
                return game.getBoard().getField(i).getFigure().isEnPassant() ? (bgColor + BLACK_FIGURE + " * " + RESET) : (bgColor + "   " + RESET);
            }
            case "CHECK_LINES": {
                return game.getBoard().getField(i).getFigure().isCheckLine() ? (bgColor + BLACK_FIGURE + " * " + RESET) : (bgColor + "   " + RESET);
            }
            case "PINNED_AND_PINNED_CHECK_LINES": {
                if (game.getBoard().getField(i).getFigure().isPinned()) {
                    return (bgColor + BLACK_FIGURE + " @ " + RESET);
                } else if (game.getBoard().getField(i).getFigure().isPinnedCheckLine()) {
                    return (bgColor + BLACK_FIGURE + " * " + RESET);
                } else {
                    return (bgColor + BLACK_FIGURE + "   " + RESET);
                }
            }
            default: {
                return " ERROR: reading data from enums.PrintBoardType ";
            }
        }
    }

    public static String defaultBoardConditions(Game game, int i, String bgColor) {

        String fColor = game.getBoard().getField(i).getFigure().getFigureColor() == FigureColor.BLACK ? BLACK_FIGURE : WHITE_FIGURE;

        if (game.getBoard().getField(i).getFigure().isSelected()) {
            bgColor = BG_SELECTED_COLOR;
        } else if (game.getBoard().getField(i).getFigure().isLegalMove()) {
            bgColor = game.getBoard().getField(i).getFigure().isEnPassant() ? BG_LEGAL_OFFENSIVE_MOVE :
                    (game.getBoard().getField(i).getFigure().getFigureColor() == Move.invertColor(game.getWhoseTurn()) ? BG_LEGAL_OFFENSIVE_MOVE : BG_LEGAL_MOVE);
        }


        switch (game.getBoard().getField(i).getFigure().getFigureType().toString()) {
            case "EMPTY": {
                return game.getBoard().getField(i).getFigure().isActivePromotion() ?
                        (BG_SELECTED_COLOR + fColor + " ? " + RESET) :
                        (bgColor + fColor + "   " + RESET);
            }
            case "PAWN": {
                return bgColor + fColor + " P " + RESET;
            }
            case "KNIGHT": {
                return bgColor + fColor + " N " + RESET;
            }
            case "BISHOP": {
                return bgColor + fColor + " B " + RESET;
            }
            case "ROOK": {
                return bgColor + fColor + " R " + RESET;
            }
            case "QUEEN": {
                return bgColor + fColor + " Q " + RESET;
            }
            case "KING": {
                return bgColor + fColor + " K " + RESET;
            }
            default: {
                return "ERROR: reading data from enums.FigureType";
            }
        }

    }

    public static String numberBoardConditions(Game game, int i, String bgColor) {
        String fColor = EMPTY_FIGURE;

        if (game.getBoard().getField(i).getFigure().getFigureColor() == game.getWhoseTurn() && game.getWhoseTurn() == FigureColor.WHITE) {
            fColor = WHITE_FIGURE;
        } else if (game.getBoard().getField(i).getFigure().getFigureColor() == game.getWhoseTurn() && game.getWhoseTurn() == FigureColor.BLACK) {
            fColor = BLACK_FIGURE;
        }

        if (game.getBoard().getField(i).getFigure().isSelected() || game.getBoard().getField(i).getFigure().isActivePromotion()) {
            bgColor = BG_SELECTED_COLOR;
        } else if (game.getBoard().getField(i).getFigure().isLegalMove()) {
            bgColor = game.getBoard().getField(i).getFigure().isEnPassant() ? BG_LEGAL_OFFENSIVE_MOVE :
                    (game.getBoard().getField(i).getFigure().getFigureColor() == Move.invertColor(game.getWhoseTurn()) ? BG_LEGAL_OFFENSIVE_MOVE : BG_LEGAL_MOVE);
            fColor = game.getWhoseTurn() == FigureColor.WHITE ? WHITE_FIGURE : BLACK_FIGURE;
        }

        return i < 10 ? (bgColor + fColor + " " + i + " " + RESET) : (bgColor + fColor + " " + i + RESET);
    }

    public static void printAllOfBoards(Game game, int selectedFigurePosition) {
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.EN_PASSANT);
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.CHECK_LINES);
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.PINNED_AND_PINNED_CHECK_LINES);
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.PROTECTED);
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.UNDER_PRESSURE);
        Printer.printBoard(game, selectedFigurePosition, PrintBoardType.NUMBERS);
    }

}



