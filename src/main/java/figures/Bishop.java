package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Bishop extends Figure {
    private final int[] moves;

    public Bishop(Color figureColor) {
        super();
        this.figureType = FigureType.BISHOP;
        this.figureColor = figureColor;
        moves = moveSide(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Bishop(Figure figure) {
        super();
        this.figureType = FigureType.BISHOP;
        this.figureColor = figure.getFigureColor();
        moves = moveSide(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public void movement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int whichMove : moves) {
            try {
                LoopedMoveSwitch(game, SELECTED, whichMove);

            } catch (Exception e) {
                movementExceptions(game,  e, SELECTED, whichMove);
            }
        }
    }
}
