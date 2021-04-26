package app;

import enums.FigureColor;

public class Player {

    FigureColor playerFigureColor;
    String playerName;

    public Player(String playerName, FigureColor playerFigureColor) {
        this.playerName = new String(playerName);
        this.playerFigureColor = playerFigureColor;
    }

    public FigureColor getPlayerColor() {
        return playerFigureColor;
    }
    public String getPlayerName() {
        return playerName;
    }
}
