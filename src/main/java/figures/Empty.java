package figures;

import app.Game;
import enums.FigureType;
import enums.Color;

public class Empty extends Figure {
    private boolean activePromotion;

    public Empty() {
        activePromotion = false;
        figureColor = Color.NONE;
        figureType = FigureType.EMPTY;
    }
    public Empty(Figure figure) {
        super();
        activePromotion = false;
        this.figureType = FigureType.EMPTY;
        this.figureColor = figure.getFigureColor();
    }

    @Override
    public void movement(Game game, int selectedFigurePosition) {
    }
}