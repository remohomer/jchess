package app;

import enums.Color;
import figures.*;
import booleans.CastlingConditions;

public class Board {
    private static int counter = 1;
    protected final int id;
    private CastlingConditions castlingConditions;
    private Field[] field;

    public Board(CastlingConditions castlingConditions, Field[] field) {
        id = counter;
        this.field = field;
        this.castlingConditions = castlingConditions;

        initializeBoard(); // if you want to initialize other Boards you have to comment "King.setLegalCastling(game)"
        counter++;
    }

    public void initializeEmptyBoard() {

        for (int i = 0; i < 64; i++) {
            this.field[i].initializeFigure(new Empty());
        }
    }

    public void initializeBoard() {
        this.field[0].initializeFigure(new Rook(Color.BLACK));
        this.field[1].initializeFigure(new Knight(Color.BLACK));
        this.field[2].initializeFigure(new Bishop(Color.BLACK));
        this.field[3].initializeFigure(new Queen(Color.BLACK));
        this.field[4].initializeFigure(new King(Color.BLACK));
        this.field[5].initializeFigure(new Bishop(Color.BLACK));
        this.field[6].initializeFigure(new Knight(Color.BLACK));
        this.field[7].initializeFigure(new Rook(Color.BLACK));

        for (int i = 8; i < 16; i++) {
            this.field[i].initializeFigure(new Pawn(Color.BLACK))
            ;
        }
        for (int i = 16; i < 48; i++) {
            this.field[i].initializeFigure(new Empty());
        }
        for (int i = 48; i < 56; i++) {
            this.field[i].initializeFigure(new Pawn(Color.WHITE));
        }

        this.field[56].initializeFigure(new Rook(Color.WHITE));
        this.field[57].initializeFigure(new Knight(Color.WHITE));
        this.field[58].initializeFigure(new Bishop(Color.WHITE));
        this.field[59].initializeFigure(new Queen(Color.WHITE));
        this.field[60].initializeFigure(new King(Color.WHITE));
        this.field[61].initializeFigure(new Bishop(Color.WHITE));
        this.field[62].initializeFigure(new Knight(Color.WHITE));
        this.field[63].initializeFigure(new Rook(Color.WHITE));
    }

    public void initializeTestBoard() {
        this.field[0].initializeFigure(new Empty());
        this.field[1].initializeFigure(new Empty());
        this.field[2].initializeFigure(new Empty());
        this.field[3].initializeFigure(new Queen(Color.BLACK));
        this.field[4].initializeFigure(new Empty());
        this.field[5].initializeFigure(new Empty());
        this.field[6].initializeFigure(new Empty());
        this.field[7].initializeFigure(new Empty());

        for (int i = 8; i < 16; i++) {
            this.field[i].initializeFigure(new Empty());
        }
        for (int i = 16; i < 48; i++) {
            this.field[i].initializeFigure(new Empty());
        }
        for (int i = 48; i < 56; i++) {
            this.field[i].initializeFigure(new Empty());
        }

        this.field[56].initializeFigure(new Empty());
        this.field[57].initializeFigure(new Empty());
        this.field[58].initializeFigure(new Empty());
        this.field[59].initializeFigure(new Queen(Color.WHITE));
        this.field[60].initializeFigure(new Empty());
        this.field[61].initializeFigure(new Empty());
        this.field[62].initializeFigure(new Empty());
        this.field[63].initializeFigure(new Empty());
    }

    public void initializeBoardTestingCheckMate() {
        initializeBoard();
        this.field[12].initializeFigure(new Empty());
        this.field[18].initializeFigure(new Knight(Color.BLACK));
        this.field[27].initializeFigure(new Bishop(Color.WHITE));
        this.field[28].initializeFigure(new Pawn(Color.BLACK));
        this.field[31].initializeFigure(new Queen(Color.WHITE));
        this.field[36].initializeFigure(new Empty());
        this.field[52].initializeFigure(new Empty());
        this.field[59].initializeFigure(new Empty());
        this.field[61].initializeFigure(new Empty());
    }

    public void initializeBoardTestingDraw() {

        initializeEmptyBoard();

        this.field[6].initializeFigure(new Bishop(Color.WHITE));
        this.field[7].initializeFigure(new King(Color.BLACK));
        this.field[62].initializeFigure(new Rook(Color.WHITE));
        this.field[63].initializeFigure(new Rook(Color.WHITE));
    }

    public void initializeBoardTestingPromotion() {
        initializeEmptyBoard();
        this.field[4].initializeFigure(new King(Color.BLACK));
        this.field[10].initializeFigure(new Pawn(Color.WHITE));
        this.field[60].initializeFigure(new King(Color.WHITE));
    }

    public int getId() {
        return id;
    }

    public CastlingConditions getCastlingConditions() {
        return castlingConditions;
    }

    public Field getField(int i) {
        return field[i];
    }

    public Field[] getWholeField() {
        return field;
    }
}

    