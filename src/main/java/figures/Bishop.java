package figures;

import app.Game;
import enums.Color;
import enums.FigureType;

public class Bishop extends Figure {
    private final int[] moves;

    public Bishop(Color figureColor) {
        super(figureColor);
        this.figureType = FigureType.BISHOP;
        moves = moveSide(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Bishop(Figure figure) {
        super(figure);
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
