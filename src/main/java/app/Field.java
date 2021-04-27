package app;

import figures.Figure;

public class Field {

    private final int number;
    private final int row;
    private final int column;
    private final String cords;
    private Figure figure;

    public Field(final int number, final int row, final int column, final String cords, Figure figure) {
        this.number = number;
        this.row = row;
        this.column = column;
        this.cords = cords;
        this.figure = figure;
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
