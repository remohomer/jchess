package figures;

import app.Game;
import booleans.FigureState;
import enums.FigureColor;
import enums.FigureType;

public class Knight extends Figure {
    private final int[] moves;

    public Knight(FigureColor figureColor) {
        super(figureColor);
        this.figureType = FigureType.KNIGHT;
        moves = moveSide(TOP_TOP_LEFT, TOP_TOP_RIGHT, BOTTOM_BOTTOM_LEFT, BOTTOM_BOTTOM_RIGHT, LEFT_LEFT_TOP, LEFT_LEFT_BOTTOM, RIGHT_RIGHT_TOP, RIGHT_RIGHT_BOTTOM);
    }

    public Knight(Figure figure) {
        super(figure);
        moves = moveSide(TOP_TOP_LEFT, TOP_TOP_RIGHT, BOTTOM_BOTTOM_LEFT, BOTTOM_BOTTOM_RIGHT, LEFT_LEFT_TOP, LEFT_LEFT_BOTTOM, RIGHT_RIGHT_TOP, RIGHT_RIGHT_BOTTOM);
    }

    @Override
    public void setLegalMovement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int moveDirection : moves) {
            final int MOVE = SELECTED + moveDirection;

            try {
                if (isOnBoard(MOVE) && !this.isPinned()) {
                    switch (moveDirection) {
                        case TOP_TOP_LEFT: {
                            if (!isSevenRow(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case TOP_TOP_RIGHT: {
                            if (!isSevenRow(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case BOTTOM_BOTTOM_LEFT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isSecondRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case BOTTOM_BOTTOM_RIGHT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isSecondRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case LEFT_LEFT_TOP: {
                            if (!isFirstColumn(game.getBoard(), SELECTED) && !isSecondColumn(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case LEFT_LEFT_BOTTOM: {
                            if (!isFirstColumn(game.getBoard(), SELECTED) && !isSecondColumn(game.getBoard(), SELECTED) && !isFirstRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case RIGHT_RIGHT_TOP: {
                            if (!isSevenColumn(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case RIGHT_RIGHT_BOTTOM: {
                            if (!isSevenColumn(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED) && !isFirstRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE, true))
                                    break;
                                MovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                movementExceptions(game, e, SELECTED, moveDirection);
            }
        }
    }

    public void MovementConditions(Game game, int MOVE, int SELECTED) {
        if (isFriendlyFigure(game, MOVE)) {
            setProtected(game, MOVE);
        } else {
            if (isEnemyKing(game,MOVE)) {
                game.getBoard().getField(SELECTED).getFigure().setCheckLine(true);
            }
            game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
            setUnderPressure(game, MOVE);
        }
    }
}
