package app;

import enums.FigureColor;

public class Printer {

    public static void printBoard(Game game) {
        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println();
                System.out.println();
                if (game.getWhichPlayer() == FigureColor.BLACK) {
                    System.out.println(" ** " + game.getPlayer2().getPlayerName() + " **");
                } else {
                    System.out.println(" " + game.getPlayer2().getPlayerName());
                }
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }

            String fColor = game.getBoard().getField(i).getFigure().getFigureColor() == FigureColor.BLACK ? "_" : " ";

            String selectedOrLegal = " ";
            if(game.getBoard().getField(i).getFigure().isSelected()) {
                selectedOrLegal = "@";
            } else if(game.getBoard().getField(i).getFigure().isLegalMove()) {
                selectedOrLegal = "*";
            }

            switch(game.getBoard().getField(i).getFigure().getFigureType().toString()) {
                case "EMPTY" : {
                    System.out.print(" " + selectedOrLegal + " ");
                    break;
                }
                case "PAWN" : {
                    System.out.print(fColor + "P" + selectedOrLegal);
                    break;
                }
                case "KNIGHT" : {
                    System.out.print(fColor + "N" + selectedOrLegal);
                    break;
                }
                case "BISHOP" : {
                    System.out.print(fColor + "B" + selectedOrLegal);
                    break;
                }
                case "ROOK" : {
                    System.out.print(fColor + "R" + selectedOrLegal);
                    break;
                }
                case "QUEEN" : {
                    System.out.print(fColor + "Q" + selectedOrLegal);
                    break;
                }
                case "KING" : {
                    System.out.print(fColor + "K" + selectedOrLegal);
                    break;
                }
            }

            if ((i + 1) % 8 == 0) {
                System.out.print("   " + row + "\n");
            }

            if (i != 0 && (i + 1) % 8 == 0) {
                row--;
            }

            if (i == 63) {
                System.out.println("\n      a  b  c  d  e  f  g  h  ");
                if (game.getWhichPlayer() == FigureColor.WHITE)
                    System.out.println("\t\t\t\t\t\t** " + game.getPlayer1().getPlayerName() + " **");
                else
                    System.out.println("\t\t\t\t\t\t" + game.getPlayer1().getPlayerName());
            }
        }
    }

    public static void printBoardWithNumbers() {

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(" " + i);
            }

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
    }

    public static void printBoardWithUnderPressure(Game game) {

        System.out.println("\n isUnderPressure()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }

            if (game.getWhichPlayer() == FigureColor.WHITE) {
                if (game.getBoard().getField(i).getFigure().isUnderPressureByBlack()) {
                    System.out.print("Up_");
                } else {
                    System.out.print("   ");
                }
            } else {
                if (game.getBoard().getField(i).getFigure().isUnderPressureByWhite()) {
                    System.out.print(" Up");
                } else {
                    System.out.print("   ");
                }
            }

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
    }

    public static void printBoardWithProtected(Game game) {

        System.out.println("\n isProtected()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }

            if (game.getWhichPlayer() == FigureColor.WHITE) {
                if (game.getBoard().getField(i).getFigure().isProtectedByBlack()) {
                    System.out.print(" Pr");
                } else {
                    System.out.print("   ");
                }
            } else {
                if (game.getBoard().getField(i).getFigure().isProtectedByWhite()) {
                    System.out.print(" Pr");
                } else {
                    System.out.print("   ");
                }
            }

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
    }

    public static void printBoardWithLegalMoves(Game game) {

        System.out.println("\n isLegalMove()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }
            if (game.getBoard().getField(i).getFigure().isLegalMove()) {
                System.out.print(" Lm");
            } else {
                System.out.print("   ");
            }

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
    }

    public static void printBoardWithPinnedAndPinnedCheckLines(Game game) {

        System.out.println("\n isPinnedFigure() && isPinnedCheckLine()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }
            if (game.getBoard().getField(i).getFigure().isPinned()) {
                System.out.print(" P ");
            } else if (game.getBoard().getField(i).getFigure().isPinnedCheckLine()) {
                System.out.print(" Pl");
            } else {
                System.out.print("   ");
            }

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
    }

    public static void printBoardWithCheckLines(Game game) {

        System.out.println("\n isCheckLine()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }
            if (game.getBoard().getField(i).getFigure().isCheckLine()) {
                System.out.print(" Cl");
            } else {
                System.out.print("   ");
            }

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
    }

    public static void printBoardWithEnPassant(Game game) {

        System.out.println("\n isEnPassant()");

        int row = 8;

        for (int i = 0; i < 64; i++) {
            if (i == 0) {
                System.out.println("      a  b  c  d  e  f  g  h  \n");
            }
            if (i % 8 == 0) {
                System.out.print(" " + row + "   ");
            }
            if (game.getBoard().getField(i).getFigure().isEnPassant()) {
                System.out.print(" Ep");
            } else {
                System.out.print("   ");
            }

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
    }

    public static void printBoardStats(Game game) {
        for (int i = 0; i < 64; i++) {
            System.out.print(i + ". ");
            System.out.print(" number: " + game.getBoard().getField(i).getNumber());
            System.out.print(" row: " + game.getBoard().getField(i).getRow());
            System.out.print(" column: " + game.getBoard().getField(i).getColumn());
            System.out.println(" cords: " + game.getBoard().getField(i).getCords());
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
            System.out.print(" | ProtByWhite: " + game.getBoard().getField(i).getFigure().isProtectedByWhite());
            System.out.print(" | PressByBlack: " + game.getBoard().getField(i).getFigure().isUnderPressureByBlack());
            System.out.print(" | ProtByBlack: " + game.getBoard().getField(i).getFigure().isProtectedByBlack());
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


