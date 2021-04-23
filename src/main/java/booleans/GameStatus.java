package booleans;

public class GameStatus {
    private boolean ACTIVE_GAME;
    private boolean DRAW;
    private boolean CHECK_MATE;

    public GameStatus() {
        ACTIVE_GAME = false;
        DRAW = false;
        CHECK_MATE = false;
    }
    public boolean isActiveGame() {
        return ACTIVE_GAME;
    }
    public void setActiveGame(boolean activeGame) {
        ACTIVE_GAME = activeGame;
    }
    public boolean isDraw() {
        return DRAW;
    }
    public void setDraw(boolean draw) {
        DRAW = draw;
    }
    public void setCheckMate(boolean checkMate) {
        CHECK_MATE = checkMate;
    }
    public boolean isCheckMate() {
        return CHECK_MATE;
    }
}