package app;

import figures.Figure;

public class Field {

    private int number;
    private int row;
    private int column;
    private String cords;
    private Figure figure;

    public Field() {

    }

    public Field(int number, int row, int column, String cords, Figure figure) {
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

    public void setNumber(int number) {
        this.number = number;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public void setCords(String cords) {
        this.cords = cords;
    }

}
