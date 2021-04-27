package app;

import enums.FigureColor;

public class Player {

    private FigureColor FigureColor;
    private String playerName;

    public Player(String playerName, FigureColor playerFigureColor) {
        this.playerName = playerName;
        this.FigureColor = playerFigureColor;
    }

    public FigureColor getPlayerFigureColor() {
        return FigureColor;
    }

    public void setPlayerFigureColor(FigureColor figureColor) {
        this.FigureColor = figureColor;
    }

    public void setPlayerFigureColor(String figureColor) {
        if (figureColor.equals("WHITE"))
            this.FigureColor = enums.FigureColor.WHITE;
        else
            this.FigureColor = enums.FigureColor.BLACK;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
