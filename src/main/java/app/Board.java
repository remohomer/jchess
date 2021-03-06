package app;

import enums.FigureColor;
import figures.*;
import booleans.CastlingConditions;


public class Board {

    private CastlingConditions castlingConditions;
    private final Field[] field;

    public Board(CastlingConditions castlingConditions, Field[] field) {
        this.field = field;
        this.castlingConditions = castlingConditions;

        initializeBoard(); // if you want to initialize other Boards you have to comment "figures.King.setLegalCastling(game)"
//        initializeMindFuckEnPassant();
    }

    public void initializeEmptyBoard() {

        for (int i = 0; i < 64; i++) {
            this.field[i].initializeFigure(new Empty());
        }
    }

    public void initializeBoard() {
        this.field[0].initializeFigure(new Rook(FigureColor.BLACK));
        this.field[1].initializeFigure(new Knight(FigureColor.BLACK));
        this.field[2].initializeFigure(new Bishop(FigureColor.BLACK));
        this.field[3].initializeFigure(new Queen(FigureColor.BLACK));
        this.field[4].initializeFigure(new King(FigureColor.BLACK));
        this.field[5].initializeFigure(new Bishop(FigureColor.BLACK));
        this.field[6].initializeFigure(new Knight(FigureColor.BLACK));
        this.field[7].initializeFigure(new Rook(FigureColor.BLACK));

        for (int i = 8; i < 16; i++) {
            this.field[i].initializeFigure(new Pawn(FigureColor.BLACK));
        }
        for (int i = 16; i < 48; i++) {
            this.field[i].initializeFigure(new Empty());
        }
        for (int i = 48; i < 56; i++) {
            this.field[i].initializeFigure(new Pawn(FigureColor.WHITE));
        }

        this.field[56].initializeFigure(new Rook(FigureColor.WHITE));
        this.field[57].initializeFigure(new Knight(FigureColor.WHITE));
        this.field[58].initializeFigure(new Bishop(FigureColor.WHITE));
        this.field[59].initializeFigure(new Queen(FigureColor.WHITE));
        this.field[60].initializeFigure(new King(FigureColor.WHITE));
        this.field[61].initializeFigure(new Bishop(FigureColor.WHITE));
        this.field[62].initializeFigure(new Knight(FigureColor.WHITE));
        this.field[63].initializeFigure(new Rook(FigureColor.WHITE));
    }

    public void initializeLoadedBoard(Game game) {
        for (int i = 0 ; i < 64 ; i ++)
        this.field[i].initializeFigure(game.getBoard().getField(i).getFigure());


    }

    public void initializeTestBoard() {
        initializeEmptyBoard();
    }

    public void initializeMindFuckEnPassant() {
        initializeEmptyBoard();
        this.field[51].initializeFigure(new Pawn(FigureColor.WHITE));
        this.field[34].initializeFigure(new Pawn(FigureColor.BLACK));
        this.field[33].initializeFigure(new King(FigureColor.BLACK));
        this.field[39].initializeFigure(new Rook(FigureColor.WHITE));

    }

    public void initializeBoardTestingEnPassant() {

        initializeBoard();
        this.field[11].initializeFigure(new Empty());
        this.field[35].initializeFigure(new Pawn(FigureColor.BLACK));
    }

    public void initializeBoardTestingCastling() {
        initializeBoard();
        this.field[1].initializeFigure(new Empty());
        this.field[2].initializeFigure(new Empty());
        this.field[3].initializeFigure(new Empty());
        this.field[5].initializeFigure(new Empty());
        this.field[6].initializeFigure(new Empty());
        this.field[57].initializeFigure(new Empty());
        this.field[58].initializeFigure(new Empty());
        this.field[59].initializeFigure(new Empty());
        this.field[61].initializeFigure(new Empty());
        this.field[62].initializeFigure(new Empty());
    }

    public void initializeBoardTestingCheckMate() {
        initializeBoard();
        this.field[12].initializeFigure(new Empty());
        this.field[18].initializeFigure(new Knight(FigureColor.BLACK));
        this.field[27].initializeFigure(new Bishop(FigureColor.WHITE));
        this.field[28].initializeFigure(new Pawn(FigureColor.BLACK));
        this.field[31].initializeFigure(new Queen(FigureColor.WHITE));
        this.field[36].initializeFigure(new Empty());
        this.field[52].initializeFigure(new Empty());
        this.field[59].initializeFigure(new Empty());
        this.field[61].initializeFigure(new Empty());
    }

    public void initializeBoardTestingDraw() {

        initializeEmptyBoard();
        this.field[6].initializeFigure(new Bishop(FigureColor.WHITE));
        this.field[7].initializeFigure(new King(FigureColor.BLACK));
        this.field[62].initializeFigure(new Rook(FigureColor.WHITE));
        this.field[63].initializeFigure(new Rook(FigureColor.WHITE));
    }

    public void initializeBoardTestingPromotion() {
        initializeEmptyBoard();
        this.field[4].initializeFigure(new King(FigureColor.BLACK));
        this.field[10].initializeFigure(new Pawn(FigureColor.WHITE));
        this.field[50].initializeFigure(new Pawn(FigureColor.BLACK));
        this.field[60].initializeFigure(new King(FigureColor.WHITE));
    }

    public Field getField(int i) {
        return field[i];
    }

    public Field[] getWholeField() {
        return field;
    }

    public CastlingConditions getCastlingConditions() {
        return castlingConditions;
    }

    public void setCastlingConditions(CastlingConditions castlingConditions) {
        this.castlingConditions = castlingConditions;
    }
}


    