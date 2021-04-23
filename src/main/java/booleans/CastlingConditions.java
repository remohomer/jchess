package booleans;

public class CastlingConditions {
    private boolean whiteKingMove;
    private boolean whiteKingCheck;
    private boolean whiteLeftRookMove;
    private boolean whiteRightRookMove;
    private boolean blackKingMove;
    private boolean blackKingCheck;
    private boolean blackLeftRookMove;
    private boolean blackRightRookMove;

    public CastlingConditions() {
        this.whiteKingMove = false;
        this.whiteKingCheck = false;
        this.whiteLeftRookMove = false;
        this.whiteRightRookMove = false;
        this.blackKingMove = false;
        this.blackKingCheck = false;
        this.blackLeftRookMove = false;
        this.blackRightRookMove = false;
    }
    public CastlingConditions(CastlingConditions castlingConditions) {
        this.whiteKingMove = castlingConditions.whiteKingMove;
        this.whiteKingCheck = castlingConditions.whiteKingCheck;
        this.whiteLeftRookMove = castlingConditions.whiteLeftRookMove;
        this.whiteRightRookMove = castlingConditions.whiteRightRookMove;
        this.blackKingMove = castlingConditions.blackKingMove;
        this.blackKingCheck = castlingConditions.blackKingCheck;
        this.blackLeftRookMove = castlingConditions.blackLeftRookMove;
        this.blackRightRookMove = castlingConditions.blackRightRookMove;
    }

    public boolean isWhiteKingMove() {
        return whiteKingMove;
    }

    public void setWhiteKingMove(boolean whiteKingMove) {
        this.whiteKingMove = whiteKingMove;
    }

    public boolean isWhiteKingCheck() {
        return whiteKingCheck;
    }

    public void setWhiteKingCheck(boolean whiteKingCheck) {
        this.whiteKingCheck = whiteKingCheck;
    }

    public boolean isWhiteLeftRookMove() {
        return whiteLeftRookMove;
    }

    public void setWhiteLeftRookMove(boolean whiteLeftRookMove) {
        this.whiteLeftRookMove = whiteLeftRookMove;
    }

    public boolean isWhiteRightRookMove() {
        return whiteRightRookMove;
    }

    public void setWhiteRightRookMove(boolean whiteRightRookMove) {
        this.whiteRightRookMove = whiteRightRookMove;
    }

    public boolean isBlackKingMove() {
        return blackKingMove;
    }

    public void setBlackKingMove(boolean blackKingMove) {
        this.blackKingMove = blackKingMove;
    }

    public boolean isBlackKingCheck() {
        return blackKingCheck;
    }

    public void setBlackKingCheck(boolean blackKingCheck) {
        this.blackKingCheck = blackKingCheck;
    }

    public boolean isBlackLeftRookMove() {
        return blackLeftRookMove;
    }

    public void setBlackLeftRookMove(boolean blackLeftRookMove) {
        this.blackLeftRookMove = blackLeftRookMove;
    }

    public boolean isBlackRightRookMove() {
        return blackRightRookMove;
    }

    public void setBlackRightRookMove(boolean blackRightRookMove) {
        this.blackRightRookMove = blackRightRookMove;
    }
}
