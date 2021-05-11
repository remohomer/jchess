package app;

import enums.FigureColor;
import enums.PrintBoardType;
import enums.Error;

public class Printer implements ConsoleColors {

    public static final int NOT_SELECTED_FIGURE = -1;
    public static final String SPACE = " ";
    public static final String TAB = "\t";


    public static void printBoard(Game game, int selectedFigurePosition, PrintBoardType secondBoardType) {

        if (selectedFigurePosition != NOT_SELECTED_FIGURE) {
            game.getBoard().getField(selectedFigurePosition).getFigure().movement(game, selectedFigurePosition);
        }
        System.out.println();

        String player = (game.getWhoseTurn() == FigureColor.BLACK)
                ? BG_CURRENT_PLAYERS_TURN + WHITE_FIGURE + SPACE + game.getPlayer2().getPlayerName() + SPACE + RESET_COLORS
                : TAB + WHITE_FIGURE + game.getPlayer2().getPlayerName() + RESET_COLORS;

        if (secondBoardType == PrintBoardType.DEFAULT) {
            System.out.println(TAB + player);
        } else {
            System.out.println(player + "\t\t\t\t\t\t\t\t\t\t" + secondBoardType.toString());
        }

        int row = 8;

        for (Field field : game.getBoard().getWholeField()) {
            if (field.getNumber() == 0) {
                System.out.println("    a  b  c  d  e  f  g  h         |         a  b  c  d  e  f  g  h");
            }

            if (field.getNumber() % 8 == 0) {
                System.out.print(SPACE + row + SPACE);
            }

            System.out.print(boardSwitch(game, field.getNumber(), PrintBoardType.DEFAULT));

            if ((field.getNumber() + 1) % 8 == 0) {
                System.out.print(SPACE + row);

                for (int secondBoardNumber = field.getNumber() - 7; secondBoardNumber <= field.getNumber(); secondBoardNumber++) {
                    if (secondBoardNumber % 8 == 0) {
                        System.out.print("      |      " + row + SPACE);
                    }
                    System.out.print(boardSwitch(game, secondBoardNumber, secondBoardType));

                    if ((secondBoardNumber + 1) % 8 == 0) {
                        System.out.print(SPACE + row + "\n");
                    }
                }
            }

            if (field.getNumber() != 0 && (field.getNumber() + 1) % 8 == 0) {
                row--;
            }

            if (field.getNumber() == 63) {
                System.out.println("    a  b  c  d  e  f  g  h         |         a  b  c  d  e  f  g  h");
            }
        }
        player = (game.getWhoseTurn() == FigureColor.WHITE)
                ? "\t\t\t\t\t" + BG_CURRENT_PLAYERS_TURN + WHITE_FIGURE + SPACE + game.getPlayer1().getPlayerName() + SPACE + RESET_COLORS
                : "\t\t\t\t\t" + WHITE_FIGURE + game.getPlayer1().getPlayerName() + RESET_COLORS;
        System.out.println(player);
    }

    public static String boardSwitch(Game game, int i, PrintBoardType printBoardType) {

        String bgColor = (game.getBoard().getField(i).getRow() % 2 == 0)
                ? (i % 2 == 0) ? BG_COLOR_1 : BG_COLOR_2
                : (i % 2 == 0) ? BG_COLOR_2 : BG_COLOR_1;

        switch (printBoardType.toString()) {
            case "DEFAULT": {
                return defaultBoardConditions(game, i, bgColor);
            }
            case "NUMBERS": {
                return numberBoardConditions(game, i, bgColor);
            }
            case "LEGAL_MOVES": {
                return (game.getBoard().getField(i).getFigure().isLegalMove()) ? createStarString(bgColor) : createEmptyString(bgColor);
            }
            case "UNDER_PRESSURE": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return (game.getBoard().getField(i).getFigure().isUnderPressureByBlack()) ? createStarString(bgColor) : createEmptyString(bgColor);
                } else {
                    return (game.getBoard().getField(i).getFigure().isUnderPressureByWhite()) ? createStarString(bgColor) : createEmptyString(bgColor);
                }
            }
            case "PROTECTED": {
                if (game.getWhoseTurn() == FigureColor.WHITE) {
                    return (game.getBoard().getField(i).getFigure().isProtectedByBlack()) ? createAtSignString(bgColor) : createEmptyString(bgColor);
                } else {
                    return (game.getBoard().getField(i).getFigure().isProtectedByWhite()) ? createAtSignString(bgColor) : createEmptyString(bgColor);
                }
            }
            case "EN_PASSANT": {
                return (game.getBoard().getField(i).getFigure().isEnPassant()) ? createStarString(bgColor) : createEmptyString(bgColor);
            }
            case "CHECK_LINES": {
                return (game.getBoard().getField(i).getFigure().isCheckLine()) ? createStarString(bgColor) : createEmptyString(bgColor);
            }
            case "PINNED_AND_PINNED_CHECK_LINES": {
                if (game.getBoard().getField(i).getFigure().isPinned()) {
                    return createAtSignString(bgColor);
                } else if (game.getBoard().getField(i).getFigure().isPinnedCheckLine()) {
                    return createStarString(bgColor);
                } else {
                    return createEmptyString(bgColor);
                }
            }
            default: {
                return Error.INCORRECT_TYPE_OF_BOARD.getMessage();
            }
        }
    }

    public static String defaultBoardConditions(Game game, int i, String bgColor) {

        String fColor = (game.getBoard().getField(i).getFigure().getFigureColor() == FigureColor.BLACK) ? BLACK_FIGURE : WHITE_FIGURE;

        if (game.getBoard().getField(i).getFigure().isSelected()) {
            bgColor = BG_SELECTED_COLOR;
        } else if (game.getBoard().getField(i).getFigure().isLegalMove()) {
            bgColor = (game.getBoard().getField(i).getFigure().isEnPassant())
                    ? BG_LEGAL_OFFENSIVE_MOVE
                    : (game.getBoard().getField(i).getFigure().getFigureColor() == Move.invertColor(game.getWhoseTurn()) ? BG_LEGAL_OFFENSIVE_MOVE : BG_LEGAL_MOVE);
        }

        switch (game.getBoard().getField(i).getFigure().getFigureType().toString()) {
            case "EMPTY": {
                return (game.getBoard().getField(i).getFigure().isActivePromotion())
                        ? (BG_SELECTED_COLOR + fColor + " ? " + RESET_COLORS)
                        : createEmptyString(bgColor);
            }
            case "PAWN": {
                return createFigureString(bgColor, fColor, "P");
            }
            case "KNIGHT": {
                return createFigureString(bgColor, fColor, "N");
            }
            case "BISHOP": {
                return createFigureString(bgColor, fColor, "B");
            }
            case "ROOK": {
                return createFigureString(bgColor, fColor, "R");
            }
            case "QUEEN": {
                return createFigureString(bgColor, fColor, "Q");
            }
            case "KING": {
                return createFigureString(bgColor, fColor, "K");
            }
            default: {
                return Error.INCORRECT_TYPE_OF_FIGURE.getMessage();
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

            bgColor = (game.getBoard().getField(i).getFigure().isEnPassant())
                    ? BG_LEGAL_OFFENSIVE_MOVE
                    : (game.getBoard().getField(i).getFigure().getFigureColor() == Move.invertColor(game.getWhoseTurn()) ? BG_LEGAL_OFFENSIVE_MOVE : BG_LEGAL_MOVE);

            fColor = (game.getWhoseTurn() == FigureColor.WHITE) ? WHITE_FIGURE : BLACK_FIGURE;
        }

        return (i < 10) ? (bgColor + fColor + SPACE + i + SPACE + RESET_COLORS) : (bgColor + fColor + SPACE + i + RESET_COLORS);
    }

    private static String createAtSignString(String bgColor) {
        return bgColor + BLACK_FIGURE + " @ " + RESET_COLORS;
    }

    private static String createStarString(String bgColor) {
        return bgColor + BLACK_FIGURE + " * " + RESET_COLORS;
    }

    private static String createEmptyString(String bgColor) {
        return bgColor + BLACK_FIGURE + "   " + RESET_COLORS;
    }

    private static String createFigureString(String bgColor, String fColor, String figureType) {
        return bgColor + fColor + SPACE + figureType + SPACE + RESET_COLORS;
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



