package figures;

import app.Game;
import enums.Color;
import enums.FigureType;

public class Empty extends Figure {
    private boolean activePromotion;

    public Empty() {
        this.figureType = FigureType.EMPTY;
        this.figureColor = Color.NONE;
        activePromotion = false;
    }
    public Empty(Figure figure) {
        super(figure);
        activePromotion = false;
    }

    @Override
    public void movement(Game game, int selectedFigurePosition) {
    }
}