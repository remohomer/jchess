package app;

import enums.Error;

public class SimpleBoard {
    private final String[] figureColor;
    private final String[] figureType;

    SimpleBoard() {
        figureColor = new String[64];
        figureType = new String[64];
    }

    public String getFigureColor(int i) {
        return figureColor[i];
    }

    public void setFigureColor(int i, String figureColor) {
        this.figureColor[i] = figureColor;
    }

    public String getFigureType(int i) {
        return figureType[i];
    }

    public void setFigureType(int i, String figureType) {
        this.figureType[i] = figureType;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            System.out.println(Error.THE_SAME_OBJECT.getMessage());
            return false;
        } else if (object == null) {
            System.out.println(Error.OBJECT_IS_NULL.getMessage());
            return false;
        } else if (getClass() != object.getClass()) {
            System.out.println(Error.INCORRECT_TYPE_OF_OBJECT.getMessage());
            return false;
        }

        SimpleBoard other = (SimpleBoard) object;

        int sameColorAndFigure = 0;
        for (int i = 0; i < 64; i++) {
            if (this.getFigureColor(i).equals(other.getFigureColor(i)) && this.getFigureType(i).equals(other.getFigureType(i)))
                sameColorAndFigure++;
        }

        return sameColorAndFigure == 64;
    }
}
