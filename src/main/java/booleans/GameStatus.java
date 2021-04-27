package booleans;

public class GameStatus {
    private boolean activeGame;
    private boolean draw;
    private boolean checkMate;

    public GameStatus() {
        activeGame = false;
        draw = false;
        checkMate = false;
    }
    public boolean isActiveGame() {
        return activeGame;
    }
    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }
    public boolean isDraw() {
        return draw;
    }
    public void setDraw(boolean draw) {
        this.draw = draw;
    }
    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }
    public boolean isCheckMate() {
        return checkMate;
    }
}