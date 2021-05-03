package figures;

import app.Game;
import enums.FigureColor;
import enums.FigureType;

public class Empty extends Figure {

    public Empty() {
        super();
        this.figureType = FigureType.EMPTY;
        this.figureColor = FigureColor.NONE;
    }
    public Empty(Figure figure) {
        super(figure);
    }

    @Override
    public void movement(Game game, int selectedFigurePosition) {
    }
}