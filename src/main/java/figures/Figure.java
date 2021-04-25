package figures;

import app.Game;
import app.Board;
import booleans.FigureState;
import enums.Color;
import enums.FigureType;

public abstract class Figure extends FigureState implements Movement {

    protected FigureType figureType;
    protected Color figureColor;

    public Figure() { }

    public Figure(Color figureColor) {
        this.figureColor = figureColor;
    }

    public Figure(Figure figure) {
        this.figureType = figure.getFigureType();
        this.figureColor = figure.getFigureColor();
    }

    public Color getFigureColor() {
        return figureColor;
    }
    public FigureType getFigureType() {
        return figureType;
    }

    public static boolean isKingCheck(Game game) {
        if (game.getWhichPlayer() == Color.WHITE) {
            return game.getBoard().getCastlingConditions().isWhiteKingCheck();
        } else {
            return game.getBoard().getCastlingConditions().isBlackKingCheck();
        }
    }

    public static boolean canIMoveWhenIsKingCheck(Game game, int MOVE, boolean setUnderPressure) {
        if (isKingCheck(game)) {
            if (game.getBoard().getField(MOVE).getFigure().isCheckLine() && game.getBoard().getField(MOVE).getFigure().getFigureType() != FigureType.KING) {
                game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                if (setUnderPressure)
                    setUnderPressure(game, MOVE);
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean canIMoveWhenIAmPinned(Game game, int MOVE, int SELECTED, boolean setUnderPressure) {
        if (!isKingCheck(game)) {
            if (game.getBoard().getField(SELECTED).getFigure().isPinned()) {
                if (game.getBoard().getField(SELECTED).getFigure().getFigureType() == FigureType.PAWN) {
                    if (game.getBoard().getField(MOVE).getFigure().isPinnedCheckLine() && isEnemyFigure(game, MOVE)) {
                        game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                        if (setUnderPressure)
                            setUnderPressure(game, MOVE);
                        return true;
                    }
                    return true;
                }
                if (game.getBoard().getField(MOVE).getFigure().isPinnedCheckLine()) {
                    game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                    if (setUnderPressure)
                        setUnderPressure(game, MOVE);
                    return true;
                }
                return true;


            }
        }
        return false;
    }

    public static void setUnderPressure(Game game, int position) {
        if (game.getWhichPlayer() == Color.WHITE)
            game.getBoard().getField(position).getFigure().setUnderPressureByWhite(true);
        else
            game.getBoard().getField(position).getFigure().setUnderPressureByBlack(true);
    }

    public static void setProtected(Game game, int position) {
        if (game.getWhichPlayer() == Color.WHITE)
            game.getBoard().getField(position).getFigure().setProtectedByWhite(true);
        else
            game.getBoard().getField(position).getFigure().setProtectedByBlack(true);
    }

    public static boolean setCheckLines(Game game, int MOVE, int whichMove, int j) {
        if (isEnemyKing(game, MOVE)) {
            if (isOnBoard(MOVE + whichMove) && isEmptyFigure(game, MOVE + whichMove)) {
                game.getBoard().getField(MOVE + whichMove).getFigure().setCheckLine(true);
                setUnderPressure(game, MOVE + whichMove);
            }
            for (int i = 0; i <= j; i++) {
                game.getBoard().getField(MOVE - whichMove * i).getFigure().setCheckLine(true);
            }
            return true;
        }
        return false;
    }

    public static boolean setPinnedCheckLines(Game game, int MOVE, int whichMove, int j) {
        if (isOnBoard(MOVE + whichMove)) {
            if (isEnemyKing(game, MOVE + whichMove)) {
                game.getBoard().getField(MOVE).getFigure().setPinned(true);
                for (int i = 0; i <= j; i++) {
                    game.getBoard().getField(MOVE - whichMove * i).getFigure().setPinnedCheckLine(true);
                }
                return true;
            }
        }
        return false;
    }

    public static void setLegalMoves(Game game, int MOVE, int SELECTED, boolean setUnderPressure) {
        if (canIMoveWhenIsKingCheck(game, MOVE, setUnderPressure)) ;
        else if (canIMoveWhenIAmPinned(game, MOVE, SELECTED, setUnderPressure)) ;
        else {
            game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
            if (setUnderPressure)
                setUnderPressure(game, MOVE);
        }
    }

    public static void movementExceptions(Game game, Exception e, int SELECTED, int whichMove) {
        System.out.println(e.getMessage());
        System.out.println("----------------------------------");
        System.out.println("FigureType: " + game.getBoard().getField(SELECTED).getFigure().figureType);
        System.out.println("FigureColor: " + game.getBoard().getField(SELECTED).getFigure().figureColor);
        System.out.println("SELECTED: " + SELECTED);
        System.out.println("whichMoves: " + whichMove);
    }

    public boolean isPawn(Board board, int position) {
        return board.getField(position).getFigure().getFigureType() == FigureType.PAWN;
    }

    public static boolean isOnBoard(int position) {
        return position >= 0 && position <= 63;
    }

    public static boolean isEmptyFigure(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureType() == FigureType.EMPTY;
    }

    public static boolean isFriendlyFigure(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureColor() == game.getWhichPlayer();
    }

    public static boolean isEnemyFigure(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureColor() != Color.NONE && game.getBoard().getField(position).getFigure().getFigureColor() != game.getWhichPlayer();
    }

    public static boolean isEnemyKing(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureType() == FigureType.KING && game.getBoard().getField(position).getFigure().getFigureColor() != game.getWhichPlayer();
    }

    public static boolean isEnPassant(Board board, int position) {
        return board.getField(position).getFigure().isEnPassant();
    }

    public static void LoopedMoveSwitch(Game game, final int SELECTED, int whichMove) {
        switch (whichMove) {
            case TOP: {
                if (!isEightRow(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case BOTTOM: {
                if (!isFirstRow(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case LEFT: {
                if (!isFirstColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case RIGHT: {
                if (!isEightColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case TOP_LEFT: {
                if (!isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case TOP_RIGHT: {
                if (!isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case BOTTOM_LEFT: {
                if (!isFirstRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
            case BOTTOM_RIGHT: {
                if (!isFirstRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED))
                    LoopedMoveInsideConditions(game, SELECTED, whichMove);
                break;
            }
        }
    }

    public static void LoopedMoveInsideConditions(Game game, final int SELECTED, int whichMove) {
        for (int j = 1; j < 8; j++) {
            final int MOVE = SELECTED + (whichMove * j);
            if (isOnBoard(MOVE)) {
                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                    break;
                if (canIMoveWhenIAmPinned(game, MOVE, SELECTED, true))
                    break;
                if (isEmptyFigure(game, MOVE)) {
                    setLegalMoves(game, MOVE, SELECTED, true);
                    if (whichMove == TOP) {
                        if (isEightRow(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == BOTTOM) {
                        if (isFirstRow(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == LEFT) {
                        if (isFirstColumn(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == RIGHT) {
                        if (isEightColumn(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == TOP_LEFT) {
                        if (isEightRow(game.getBoard(), MOVE) || isFirstColumn(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == TOP_RIGHT) {
                        if (isEightRow(game.getBoard(), MOVE) || isEightColumn(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == BOTTOM_LEFT) {
                        if (isFirstRow(game.getBoard(), MOVE) || isFirstColumn(game.getBoard(), MOVE))
                            break;
                    } else if (whichMove == BOTTOM_RIGHT) {
                        if (isFirstRow(game.getBoard(), MOVE) || isEightColumn(game.getBoard(), MOVE))
                            break;
                    }
                } else if (isEnemyFigure(game, MOVE)) {
                    setLegalMoves(game, MOVE, SELECTED, true);
                    if (setCheckLines(game, MOVE, whichMove, j))
                        break;
                    if (setPinnedCheckLines(game, MOVE, whichMove, j))
                        break;
                    break;
                } else {
                    setProtected(game, MOVE);
                    break;
                }
            }
        }
    }

    public static boolean isFirstRow(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getRow() == 1;
    }

    public static boolean isSecondRow(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getRow() == 2;
    }

    public static boolean isSevenRow(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getRow() == 7;
    }

    public static boolean isEightRow(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getRow() == 8;
    }

    public static boolean isFirstColumn(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getColumn() == 1;
    }

    public static boolean isSecondColumn(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getColumn() == 2;
    }

    public static boolean isSevenColumn(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getColumn() == 7;
    }

    public static boolean isEightColumn(Board board, int selectedFigurePosition) {
        return board.getField(selectedFigurePosition).getColumn() == 8;
    }
}
