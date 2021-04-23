package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Knight extends Figure {
    private final int[] moves;

    public Knight(Color figureColor) {
        super();
        figureType = FigureType.KNIGHT;
        this.figureColor = figureColor;
        moves = moveSide(TOP_TOP_LEFT, TOP_TOP_RIGHT, BOTTOM_BOTTOM_LEFT, BOTTOM_BOTTOM_RIGHT, LEFT_LEFT_TOP, LEFT_LEFT_BOTTOM, RIGHT_RIGHT_TOP, RIGHT_RIGHT_BOTTOM);
    }

    public Knight(Figure figure) {
        super();
        this.figureType = FigureType.KNIGHT;
        this.figureColor = figure.getFigureColor();
        moves = moveSide(TOP_TOP_LEFT, TOP_TOP_RIGHT, BOTTOM_BOTTOM_LEFT, BOTTOM_BOTTOM_RIGHT, LEFT_LEFT_TOP, LEFT_LEFT_BOTTOM, RIGHT_RIGHT_TOP, RIGHT_RIGHT_BOTTOM);
    }

    @Override
    public void movement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int whichMove : moves) {
            final int MOVE = SELECTED + whichMove;

            try {
                if (isOnBoard(MOVE) && !this.isPinned()) {
                    switch (whichMove) {
                        case TOP_TOP_LEFT: {
                            if (!isSevenRow(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case TOP_TOP_RIGHT: {
                            if (!isSevenRow(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case BOTTOM_BOTTOM_LEFT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isSecondRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case BOTTOM_BOTTOM_RIGHT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isSecondRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case LEFT_LEFT_TOP: {
                            if (!isFirstColumn(game.getBoard(), SELECTED) && !isSecondColumn(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case LEFT_LEFT_BOTTOM: {
                            if (!isFirstColumn(game.getBoard(), SELECTED) && !isSecondColumn(game.getBoard(), SELECTED) && !isFirstRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case RIGHT_RIGHT_TOP: {
                            if (!isSevenColumn(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED) && !isEightRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case RIGHT_RIGHT_BOTTOM: {
                            if (!isSevenColumn(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED) && !isFirstRow(game.getBoard(), SELECTED)) {
                                if (canIMoveWhenIsKingCheck(game, MOVE,true))
                                    break;
                                MovementConditions(game, MOVE);
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                movementExceptions(game, e, SELECTED, whichMove);
            }
        }
    }

    public void MovementConditions(Game game, int MOVE) {
        if (isFriendlyFigure(game, MOVE))
            setProtected(game, MOVE);
        else {
            game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
            setUnderPressure(game, MOVE);
        }
    }
}
