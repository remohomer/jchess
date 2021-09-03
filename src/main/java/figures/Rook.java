package figures;

import app.Game;
import enums.FigureColor;
import enums.FigureType;

public class Rook extends Figure {
    private final int[] moves;

    public Rook(FigureColor figureColor) {
        super(figureColor);
        this.figureType = FigureType.ROOK;
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT);
    }

    public Rook(Figure figure) {
        super(figure);
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT);
    }

    @Override
    public void setLegalMovement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int moveDirection : moves) {
            try {
                LoopedMoveSwitch(game, SELECTED, moveDirection);
            } catch (Exception e) {
                movementExceptions(game,  e, SELECTED, moveDirection);
            }
        }
    }
}
