package booleans;

public class FigureState {

    private boolean selected;
    private boolean pinned;
    private boolean checkLine;
    private boolean pinnedCheckLine;
    private boolean enPassant;
    private boolean protectedByWhite;
    private boolean protectedByBlack;
    private boolean underPressureByWhite;
    private boolean underPressureByBlack;

    private boolean legalMove;

    public FigureState() {
        selected = false;
        pinned = false;
        checkLine = false;
        pinnedCheckLine = false;
        enPassant = false;
        protectedByWhite = false;
        protectedByBlack = false;
        underPressureByWhite = false;
        underPressureByBlack = false;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isCheckLine() {
        return checkLine;
    }

    public void setCheckLine(boolean checkLine) {
        this.checkLine = checkLine;
    }

    public boolean isLegalMove() {
        return legalMove;
    }

    public void setLegalMove(boolean legalMove) {
        this.legalMove = legalMove;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public boolean isProtectedByWhite() {
        return protectedByWhite;
    }

    public void setProtectedByWhite(boolean protectedByWhite) {
        this.protectedByWhite = protectedByWhite;
    }

    public boolean isProtectedByBlack() {
        return protectedByBlack;
    }

    public void setProtectedByBlack(boolean protectedByBlack) {
        this.protectedByBlack = protectedByBlack;
    }

    public boolean isUnderPressureByWhite() {
        return underPressureByWhite;
    }

    public void setUnderPressureByWhite(boolean underPressureByWhite) {
        this.underPressureByWhite = underPressureByWhite;
    }

    public boolean isUnderPressureByBlack() {
        return underPressureByBlack;
    }

    public void setUnderPressureByBlack(boolean underPressureByBlack) {
        this.underPressureByBlack = underPressureByBlack;
    }

    public boolean isPinnedCheckLine() {
        return pinnedCheckLine;
    }

    public void setPinnedCheckLine(boolean pinnedCheckLine) {
        this.pinnedCheckLine = pinnedCheckLine;
    }
}
