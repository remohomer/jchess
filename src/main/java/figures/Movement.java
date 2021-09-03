package figures;

import app.Game;

public interface Movement {
    int TOP = -8;
    int BOTTOM = 8;
    int LEFT = -1;
    int RIGHT = 1;
    int TOP_LEFT = -9;
    int TOP_RIGHT = -7;
    int BOTTOM_LEFT = 7;
    int BOTTOM_RIGHT = 9;
    int TOP_TOP = -16;
    int BOTTOM_BOTTOM = 16;
    int TOP_TOP_LEFT = -17;
    int TOP_TOP_RIGHT = -15;
    int BOTTOM_BOTTOM_LEFT = 15;
    int BOTTOM_BOTTOM_RIGHT = 17;
    int LEFT_LEFT_TOP = -10;
    int LEFT_LEFT_BOTTOM = 6;
    int RIGHT_RIGHT_TOP = -6;
    int RIGHT_RIGHT_BOTTOM = 10;

    void setLegalMovement(Game game, final int selectedFigurePosition);

    default int[] moveSide(int... moveSide) {
        return moveSide;
    }
}
