package figures;

import app.Game;
import enums.FigureColor;
import enums.FigureType;

public class Queen extends Figure {
    private final int[] moves;

    public Queen(FigureColor figureColor) {
        super(figureColor);
        this.figureType = FigureType.QUEEN;
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    public Queen(Figure figure) {
        super(figure);
        moves = moveSide(TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public void setLegalMovement(Game game, final int SELECTED) {
        this.setSelected(true);

        for (int moveDirection : moves) {
            try {
                LoopedMoveSwitch(game, SELECTED, moveDirection);
            } catch (Exception e) {
                movementExceptions(game, e, SELECTED, moveDirection);
            }
        }
    }
}
