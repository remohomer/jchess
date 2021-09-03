package figures;

import app.Game;
import app.Board;
import booleans.FigureState;
import enums.FigureColor;
import enums.FigureType;

public abstract class Figure extends FigureState implements Movement {


    public abstract void setLegalMovement(Game game, final int selectedFigurePosition);

    protected FigureType figureType;
    protected FigureColor figureColor;
    protected boolean activePromotion;

    public Figure() {
        activePromotion = false;
    }

    public Figure(FigureColor figureColor) {
        this.figureColor = figureColor;
        activePromotion = false;
    }

    public Figure(Figure figure) {
        this.figureType = figure.getFigureType();
        this.figureColor = figure.getFigureColor();
        activePromotion = false;
    }

    public FigureColor getFigureColor() {
        return figureColor;
    }

    public FigureType getFigureType() {
        return figureType;
    }

    public static boolean isKingCheck(Game game) {
        if (game.getWhoseTurn() == FigureColor.WHITE) {
            return game.getBoard().getCastlingConditions().isWhiteKingCheck();
        } else {
            return game.getBoard().getCastlingConditions().isBlackKingCheck();
        }
    }

    public static void setUnderPressure(Game game, int position) {
        if (game.getWhoseTurn() == FigureColor.WHITE) {
            game.getBoard().getField(position).getFigure().setUnderPressureByWhite(true);
        } else {
            game.getBoard().getField(position).getFigure().setUnderPressureByBlack(true);
        }
    }

    public static void setProtected(Game game, int position) {
        if (game.getWhoseTurn() == FigureColor.WHITE) {
            game.getBoard().getField(position).getFigure().setProtectedByWhite(true);
        } else {
            game.getBoard().getField(position).getFigure().setProtectedByBlack(true);
        }
    }

    public static void setLegalMoves(Game game, final int MOVE, final int SELECTED, boolean setUnderPressure) {
        if (canIMoveWhenIsKingCheck(game, MOVE, setUnderPressure)) {
        } else if (canIMoveWhenIAmPinned(game, MOVE, SELECTED, setUnderPressure)) {
        } else {
            game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
            if (setUnderPressure)
                setUnderPressure(game, MOVE);
        }
    }

    public static void movementExceptions(Game game, Exception e, final int SELECTED, final int MOVE_DIRECTION) {
        System.out.println(e.getMessage());
        System.out.println("----------------------------------");
        System.out.println("FigureType: " + game.getBoard().getField(SELECTED).getFigure().figureType);
        System.out.println("FigureColor: " + game.getBoard().getField(SELECTED).getFigure().figureColor);
        System.out.println("SELECTED: " + SELECTED);
        System.out.println("MOVE_DIRECTIONs: " + MOVE_DIRECTION);
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
        return game.getBoard().getField(position).getFigure().getFigureColor() == game.getWhoseTurn();
    }

    public static boolean isEnemyFigure(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureColor() != FigureColor.NONE && game.getBoard().getField(position).getFigure().getFigureColor() != game.getWhoseTurn();
    }

    public static boolean isEnemyKing(Game game, int position) {
        return game.getBoard().getField(position).getFigure().getFigureType() == FigureType.KING && game.getBoard().getField(position).getFigure().getFigureColor() != game.getWhoseTurn();
    }

    public static void LoopedMoveSwitch(Game game, final int SELECTED, final int MOVE_DIRECTION) {
        switch (MOVE_DIRECTION) {
            case TOP: {
                if (!isEightRow(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case BOTTOM: {
                if (!isFirstRow(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case LEFT: {
                if (!isFirstColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case RIGHT: {
                if (!isEightColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case TOP_LEFT: {
                if (!isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case TOP_RIGHT: {
                if (!isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case BOTTOM_LEFT: {
                if (!isFirstRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
            case BOTTOM_RIGHT: {
                if (!isFirstRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                    LoopedMoveInsideConditions(game, SELECTED, MOVE_DIRECTION);
                }
                break;
            }
        }
    }

    public static void LoopedMoveInsideConditions(Game game, final int SELECTED, final int MOVE_DIRECTION) {
        for (int beforePinned = 1; beforePinned < 8; beforePinned++) {
            final int MOVE = SELECTED + (MOVE_DIRECTION * beforePinned);
            if (isOnBoard(MOVE)) {
                if (canIMoveWhenIsKingCheck(game, MOVE, true)) {
                    if(!isOnBoard(MOVE)) {
                        break;
                    }
                }
                if (canIMoveWhenIAmPinned(game, MOVE, SELECTED, true)) {
                    // TODO: samo sprawdzenie warunku ustawiło pole. Dlaczego usunięcie break rozwiązało problem?
                }
                if (isEmptyFigure(game, MOVE)) {
                    setLegalMoves(game, MOVE, SELECTED, true);
                    if (MOVE_DIRECTION == TOP) {
                        if (isEightRow(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == BOTTOM) {
                        if (isFirstRow(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == LEFT) {
                        if (isFirstColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == RIGHT) {
                        if (isEightColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == TOP_LEFT) {
                        if (isEightRow(game.getBoard(), MOVE) || isFirstColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == TOP_RIGHT) {
                        if (isEightRow(game.getBoard(), MOVE) || isEightColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == BOTTOM_LEFT) {
                        if (isFirstRow(game.getBoard(), MOVE) || isFirstColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    } else if (MOVE_DIRECTION == BOTTOM_RIGHT) {
                        if (isFirstRow(game.getBoard(), MOVE) || isEightColumn(game.getBoard(), MOVE)) {
                            break;
                        }
                    }
                } else if (isEnemyFigure(game, MOVE)) {
                    // set protected to friendlyFigure if is behind of enemyKing
                    try {
                        if (isEnemyKing(game, MOVE) && isFriendlyFigure(game, MOVE + MOVE_DIRECTION)) {
                            setProtected(game, MOVE + MOVE_DIRECTION);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(e);
                    }
                    setLegalMoves(game, MOVE, SELECTED, true);

                    if (setCheckLines(game, MOVE, MOVE_DIRECTION, beforePinned)) {
                        // TODO: po co mi tu breake był?
                    }
                    if (setPinnedCheckLines(game, MOVE, MOVE_DIRECTION, beforePinned)) {
                        // TODO: po co mi tu breake był?
                    }
                    break;
                } else {
                    setProtected(game, MOVE);
                    break;
                }
            }
        }
    }

    public static boolean canIMoveWhenIsKingCheck(Game game, final int MOVE, boolean setUnderPressure) {
        if (isKingCheck(game)) {
            if (game.getBoard().getField(MOVE).getFigure().isCheckLine() && game.getBoard().getField(MOVE).getFigure().getFigureType() != FigureType.KING) {
                game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                if (setUnderPressure) {
                    setUnderPressure(game, MOVE);
                }
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean canIMoveWhenIAmPinned(Game game, final int MOVE, final int SELECTED, boolean setUnderPressure) {
        if (!isKingCheck(game)) {
            if (game.getBoard().getField(SELECTED).getFigure().isPinned()) {
                if (game.getBoard().getField(SELECTED).getFigure().getFigureType() == FigureType.PAWN) {
                    if (game.getBoard().getField(MOVE).getFigure().isPinnedCheckLine() && isEnemyFigure(game, MOVE)) {
                        game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                        if (setUnderPressure) {
                            setUnderPressure(game, MOVE);
                        }
                        return true;
                    } else if (game.getBoard().getField(MOVE).getFigure().isPinnedCheckLine() && isEmptyFigure(game, MOVE)) {
                        game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                        return true;
                    }
                }
                if (game.getBoard().getField(MOVE).getFigure().isPinnedCheckLine()) {
                    game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
                    if (setUnderPressure) {
                        setUnderPressure(game, MOVE);
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean setCheckLines(Game game, final int MOVE, final int MOVE_DIRECTION, int beforePinned) {
        if (isEnemyKing(game, MOVE)) {
            if (isOnBoard(MOVE + MOVE_DIRECTION) && isEmptyFigure(game, MOVE + MOVE_DIRECTION)) {
                game.getBoard().getField(MOVE + MOVE_DIRECTION).getFigure().setCheckLineBehindKing(true);
                setUnderPressure(game, MOVE + MOVE_DIRECTION);
            }
            for (int i = 0; i <= beforePinned; i++) {
                game.getBoard().getField(MOVE - MOVE_DIRECTION * i).getFigure().setCheckLine(true);
            }
            return true;
        }
        return false;
    }

    public static boolean setPinnedCheckLines(Game game, final int MOVE, final int MOVE_DIRECTION, int beforePinned) {
        for (int afterPinned = 1; afterPinned <= 6; afterPinned++) {
            if (isOnBoard(MOVE + MOVE_DIRECTION * afterPinned)) {
                if (isEnemyFigure(game, MOVE + MOVE_DIRECTION * afterPinned) && !isEnemyKing(game, MOVE + MOVE_DIRECTION * afterPinned)) {
                    return false;
                }
                if (isEnemyKing(game, MOVE + MOVE_DIRECTION * afterPinned)) {
                    game.getBoard().getField(MOVE).getFigure().setPinned(true);

                    for (int i = 0; i < afterPinned; i++) {
                        game.getBoard().getField(MOVE + MOVE_DIRECTION * i).getFigure().setPinnedCheckLine(true);
                    }

                    for (int i = 1; i <= beforePinned; i++) {
                        game.getBoard().getField(MOVE - MOVE_DIRECTION * i).getFigure().setPinnedCheckLine(true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFirstRow(Board board, final int SELECTED) {
        return board.getField(SELECTED).getRow() == 1;
    }

    public static boolean isSecondRow(Board board, final int SELECTED) {
        return board.getField(SELECTED).getRow() == 2;
    }

    public static boolean isSevenRow(Board board, final int SELECTED) {
        return board.getField(SELECTED).getRow() == 7;
    }

    public static boolean isEightRow(Board board, final int SELECTED) {
        return board.getField(SELECTED).getRow() == 8;
    }

    public static boolean isFirstColumn(Board board, final int SELECTED) {
        return board.getField(SELECTED).getColumn() == 1;
    }

    public static boolean isSecondColumn(Board board, final int SELECTED) {
        return board.getField(SELECTED).getColumn() == 2;
    }

    public static boolean isSevenColumn(Board board, final int SELECTED) {
        return board.getField(SELECTED).getColumn() == 7;
    }

    public static boolean isEightColumn(Board board, final int SELECTED) {
        return board.getField(SELECTED).getColumn() == 8;
    }

    public boolean isActivePromotion() {
        return activePromotion;
    }

    public void setActivePromotion(boolean activePromotion) {
        this.activePromotion = activePromotion;
    }
}
