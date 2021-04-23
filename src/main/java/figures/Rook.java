package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Rook extends Figure {
    private final int[] moves;

    public Rook(Color figureColor) {
        super();
        figureType = FigureType.ROOK;
        this.figureColor = figureColor;
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT);
    }

    public Rook(Figure figure) {
        super();
        this.figureType = FigureType.ROOK;
        this.figureColor = figure.getFigureColor();
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT);
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
