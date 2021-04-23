package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Queen extends Figure {

    private final int[] moves;

    public Queen(Color figureColor) {
        super();
        figureType = FigureType.QUEEN;
        this.figureColor = figureColor;
        moves = moveSide(TOP, BOTTOM,LEFT,RIGHT, RIGHT,TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Queen(Figure figure) {
        super();
        this.figureType = FigureType.QUEEN;
        this.figureColor = figure.getFigureColor();
        moves = moveSide(TOP, BOTTOM,LEFT,RIGHT,TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
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
