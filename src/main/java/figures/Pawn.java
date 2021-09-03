package figures;

import app.Game;
import app.Move;
import app.Printer;
import enums.FigureType;
import enums.FigureColor;
import enums.PrintBoardType;

public class Pawn extends Figure {

    public final int[] moves;

    public Pawn(FigureColor figureColor) {
        super(figureColor);
        this.figureType = FigureType.PAWN;
        moves = (figureColor == FigureColor.WHITE)
                ? moveSide(TOP, TOP_TOP, TOP_LEFT, TOP_RIGHT)
                : moveSide(BOTTOM, BOTTOM_BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Pawn(Figure figure) {
        super(figure);
        moves = (figureColor == FigureColor.WHITE)
                ? moveSide(TOP, TOP_TOP, TOP_LEFT, TOP_RIGHT)
                : moveSide(BOTTOM, BOTTOM_BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public void setLegalMovement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int moveDirection : moves) {

            final int MOVE = SELECTED + moveDirection;
            try {
                if (isOnBoard(MOVE)) {
                    switch (moveDirection) {
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
                movementExceptions(game, e, SELECTED, moveDirection);
            }
        }
    }

    public static boolean isEnPassant(Game game, int position) {
        if (game.getWhoseTurn() == FigureColor.WHITE) {
            return game.getBoard().getField(position).getFigure().isEnPassantForWhite();
        } else if (game.getWhoseTurn() == FigureColor.BLACK) {
            return game.getBoard().getField(position).getFigure().isEnPassantForBlack();
        }
        return false;
    }

    public static void PawnsDiagonallyMovementConditions(Game game, int MOVE, int SELECTED) {
        if (isEmptyFigure(game, MOVE) && isEnPassant(game, MOVE)) {
            setLegalMoves(game, MOVE, SELECTED, true);
        }
        else if (isEmptyFigure(game, MOVE))
            setUnderPressure(game, MOVE);
        else if (isEnemyFigure(game, MOVE))
            setLegalMoves(game, MOVE, SELECTED, true);
        else if (isFriendlyFigure(game, MOVE))
            setProtected(game, MOVE);
    }
}
