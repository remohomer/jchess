package app;

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
    public boolean equals(Object ob) {

        int counter = 0;

        for (int i = 0; i < 64; i++) {
            if (this.getFigureColor(i).equals(((SimpleBoard) ob).getFigureColor(i)) && this.getFigureType(i).equals(((SimpleBoard) ob).getFigureType(i)))
                counter++;
        }

        return counter == 64;
    }
}
