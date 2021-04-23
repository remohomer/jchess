package app;

import figures.Empty;
import figures.Figure;

public class Field {

    private final int number;
    private final int row;
    private final int column;
    private final String cords;
    private Figure figure;

    public Field(int number, int row, int column, String cords) {
        this.number = number;
        this.row = row;
        this.column = column;
        this.cords = cords;
        figure = new Empty();
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public void initializeFigure(Figure figure) {
        this.figure = figure;
    }

    public int getNumber() {
        return number;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getCords() {
        return cords;
    }
}
