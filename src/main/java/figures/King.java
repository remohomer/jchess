package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class King extends Figure {

    public final int[] moves;

    public King(Color figureColor) {
        super();
        figureType = FigureType.KING;
        this.figureColor = figureColor;
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public King(Figure figure) {
        super();
        this.figureType = FigureType.KING;
        this.figureColor = figure.getFigureColor();
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public void movement(Game game, final int SELECTED) {
        this.setSelected(true);
        setLegalCastling(game); // comment this line if you are testing something on not default board

        for (int whichMove : moves) {
            final int MOVE = SELECTED + whichMove;

            try {
                if (isOnBoard(MOVE)) {
                    switch (whichMove) {
                        case TOP: {
                            if (!isEightRow(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case BOTTOM: {
                            if (!isFirstRow(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case LEFT: {
                            if (!isFirstColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case RIGHT: {
                            if (!isEightColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case TOP_LEFT: {
                            if (!isEightRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case TOP_RIGHT: {
                            if (!isEightRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case BOTTOM_LEFT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isFirstColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
                            }
                            break;
                        }
                        case BOTTOM_RIGHT: {
                            if (!isFirstRow(game.getBoard(), SELECTED) && !isEightColumn(game.getBoard(), SELECTED)) {
                                if (!isUnderPressureOrProtected(game, MOVE))
                                    kingMovementConditions(game, MOVE);
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

    public void setLegalCastling(Game game) {
        if (isWhiteKingSideCastleActive(game))
            game.getBoard().getField(62).getFigure().setLegalMove(true);
        if (isWhiteQueenSideCastleActive(game))
            game.getBoard().getField(58).getFigure().setLegalMove(true);
        if (isBlackKingSideCastleActive(game))
            game.getBoard().getField(6).getFigure().setLegalMove(true);
        if (isBlackQueenSideCastleActive(game))
            game.getBoard().getField(2).getFigure().setLegalMove(true);
    }

    public static boolean isWhiteKingSideCastleActive(Game game) {
        return !game.getBoard().getCastlingConditions().isWhiteKingCheck()
                && !game.getBoard().getCastlingConditions().isWhiteKingMove()
                && !game.getBoard().getCastlingConditions().isWhiteRightRookMove()
                && !game.getBoard().getField(61).getFigure().isUnderPressureByBlack()
                && !game.getBoard().getField(62).getFigure().isUnderPressureByBlack()
                && game.getBoard().getField(61).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(62).getFigure().getFigureType() == FigureType.EMPTY;
    }

    public static boolean isWhiteQueenSideCastleActive(Game game) {
        return !game.getBoard().getCastlingConditions().isWhiteKingCheck()
                && !game.getBoard().getCastlingConditions().isWhiteKingMove()
                && !game.getBoard().getCastlingConditions().isWhiteLeftRookMove()
                && !game.getBoard().getField(58).getFigure().isUnderPressureByBlack()
                && !game.getBoard().getField(59).getFigure().isUnderPressureByBlack()
                && game.getBoard().getField(57).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(58).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(59).getFigure().getFigureType() == FigureType.EMPTY;
    }

    public static boolean isBlackKingSideCastleActive(Game game) {
        return !game.getBoard().getCastlingConditions().isBlackKingCheck()
                && !game.getBoard().getCastlingConditions().isBlackKingMove()
                && !game.getBoard().getCastlingConditions().isBlackRightRookMove()
                && !game.getBoard().getField(5).getFigure().isUnderPressureByWhite()
                && !game.getBoard().getField(6).getFigure().isUnderPressureByWhite()
                && game.getBoard().getField(5).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(6).getFigure().getFigureType() == FigureType.EMPTY;
    }

    public static boolean isBlackQueenSideCastleActive(Game game) {
        return !game.getBoard().getCastlingConditions().isBlackKingCheck()
                && !game.getBoard().getCastlingConditions().isBlackKingMove()
                && !game.getBoard().getCastlingConditions().isBlackLeftRookMove()
                && !game.getBoard().getField(2).getFigure().isUnderPressureByWhite()
                && !game.getBoard().getField(3).getFigure().isUnderPressureByWhite()
                && game.getBoard().getField(1).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(2).getFigure().getFigureType() == FigureType.EMPTY
                && game.getBoard().getField(3).getFigure().getFigureType() == FigureType.EMPTY;
    }

    public boolean isUnderPressureOrProtected(Game game, int MOVE) {
        if (game.getWhichPlayer() == Color.WHITE)
            return game.getBoard().getField(MOVE).getFigure().isUnderPressureByBlack() || game.getBoard().getField(MOVE).getFigure().isProtectedByBlack();
        else
            return game.getBoard().getField(MOVE).getFigure().isUnderPressureByWhite() || game.getBoard().getField(MOVE).getFigure().isProtectedByWhite();

    }

    public void kingMovementConditions(Game game, int MOVE) {
        if (isFriendlyFigure(game, MOVE))
            setProtected(game, MOVE);
        else {
            game.getBoard().getField(MOVE).getFigure().setLegalMove(true);
            setUnderPressure(game, MOVE);
        }
    }
}
