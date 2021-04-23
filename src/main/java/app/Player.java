package app;

import enums.Color;

public class Player {

    Color playerColor;
    String playerName;

    public Player(String playerName, Color playerColor) {
        this.playerName = new String(playerName);
        this.playerColor = playerColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }
    public String getPlayerName() {
        return playerName;
    }
}
