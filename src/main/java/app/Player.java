package app;

import enums.FigureColor;

public class Player {

    private FigureColor playerColor;
    private String playerName;

    public Player(String playerName, FigureColor playerColor) {
        this.playerName = playerName;
        this.playerColor = playerColor;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public FigureColor getPlayerColor() {
        return playerColor;
    }
}
