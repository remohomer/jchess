package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Pawn extends Figure {

    public final int[] moves;

    public Pawn(Color figureColor) {
        super();
        figureType = FigureType.PAWN;
        this.figureColor = figureColor;

        if (figureColor == Color.WHITE)
            moves = moveSide(TOP, TOP_TOP, TOP_LEFT, TOP_RIGHT);
        else
            moves = moveSide(BOTTOM, BOTTOM_BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Pawn(Figure figure) {
        super();
        this.figureType = FigureType.PAWN;
        this.figureColor = figure.getFigureColor();
        if (figureColor == Color.WHITE)
            moves = moveSide(TOP, TOP_TOP, TOP_LEFT, TOP_RIGHT);
        else
            moves = moveSide(BOTTOM, BOTTOM_BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT);
    }


    public static void PawnsDiagonallyMovementConditions(Game game, int MOVE, int SELECTED) {
        if (isEmptyFigure(game, MOVE) && isEnPassant(game.getBoard(), MOVE))
            setLegalMoves(game, MOVE, SELECTED, true);
        else if (isEmptyFigure(game, MOVE))
            setUnderPressure(game, MOVE);
        else if (isEnemyFigure(game, MOVE))
            setLegalMoves(game, MOVE, SELECTED, true);
        else if (isFriendlyFigure(game, MOVE))
            setProtected(game, MOVE);
    }

    @Override
    public void movement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int whichMove : moves) {

            final int MOVE = SELECTED + whichMove;

            try {
                if (isOnBoard(MOVE)) {
                    switch (whichMove) {
                        case TOP: {
                            if (!isEightRow(game.getBoard(), SELECTED)) {
                                if (isEmptyFigure(game, MOVE)) {
                                    setLegalMoves(game, MOVE, SELECTED, false);
                                }
                            }
                            break;
                        }
                        case TOP_TOP: {
                            if (isSecondRow(game.getBoard(), SELECTED)) {
                                if (isEmptyFigure(game, MOVE) && isEmptyFigure(game, SELECTED + TOP)) {
                                    setLegalMoves(game, MOVE, SELECTED, false);
                                }
                            }
                            break;
                        }
                        case TOP_LEFT: {
                            if (!isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                PawnsDiagonallyMovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case TOP_RIGHT: {
                            if (!isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                PawnsDiagonallyMovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case BOTTOM: {
                            if (!isFirstRow(game.getBoard(), SELECTED)) {
                                if (isEmptyFigure(game, MOVE))
                                    setLegalMoves(game, MOVE, SELECTED, false);
                            }
                            break;
                        }
                        case BOTTOM_BOTTOM: {
                            if (isSevenRow(game.getBoard(), SELECTED)) {
                                if (isEmptyFigure(game, MOVE) && isEmptyFigure(game, SELECTED + BOTTOM))
                                    setLegalMoves(game, MOVE, SELECTED, false);
                            }
                            break;
                        }
                        case BOTTOM_LEFT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                PawnsDiagonallyMovementConditions(game, MOVE, SELECTED);
                            }
                            break;
                        }
                        case BOTTOM_RIGHT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                PawnsDiagonallyMovementConditions(game, MOVE, SELECTED);
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

}
