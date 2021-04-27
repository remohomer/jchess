package app;

import enums.FigureColor;
import figures.*;

import java.io.*;
import java.util.StringTokenizer;

public class FileManager {

    private static final int NAME_LENGHT = 30;
    private static final int COLOR_LENGHT = 5;
    private static final int TYPE_LENGHT = 6;
    private static int moveCounter = 0;
    private static int theSameBoard = 0;

    public static void saveGameToByteFile(Game game, String fileName) {
        try {
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(fileName + ".txt"));

            StringBuffer player1Name = new StringBuffer((NAME_LENGHT));
            player1Name.append(game.getPlayer1().getPlayerName());
            player1Name.setLength(NAME_LENGHT);
            outS.writeChars(player1Name.toString());

            StringBuffer player2Name = new StringBuffer((NAME_LENGHT));
            player2Name.append(game.getPlayer2().getPlayerName());
            player2Name.setLength(NAME_LENGHT);
            outS.writeChars(player2Name.toString());

            if (game.getWhichPlayer() == FigureColor.WHITE)
                outS.writeInt(1);
            else
                outS.writeInt(2);

            for (int i = 0; i < 64; i++) {
                outS.writeInt(game.getBoard().getField(i).getNumber());

                StringBuffer figureColor = new StringBuffer(COLOR_LENGHT);
                figureColor.append(game.getBoard().getField(i).getFigure().getFigureColor());
                figureColor.setLength(COLOR_LENGHT);
                outS.writeChars(figureColor.toString());

                StringBuffer figureType = new StringBuffer(TYPE_LENGHT);
                figureType.append(game.getBoard().getField(i).getFigure().getFigureType());
                figureType.setLength(TYPE_LENGHT);
                outS.writeChars(figureType.toString());
            }
            outS.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Game loadByteFileToGame(String fileName) {

        Game game = new Game(InitializeGame.newGame());

        try {
            DataInputStream inS = new DataInputStream(new FileInputStream(fileName + ".txt"));

            StringBuffer playerName1 = new StringBuffer(COLOR_LENGHT);
            for (int j = 0; j < NAME_LENGHT; j++) {
                char tChar = inS.readChar();
                if (tChar != '\0')
                    playerName1.append(tChar);
            }

            StringBuffer playerName2 = new StringBuffer(COLOR_LENGHT);
            for (int j = 0; j < NAME_LENGHT; j++) {
                char tChar = inS.readChar();
                if (tChar != '\0')
                    playerName2.append(tChar);
            }

            if (inS.readInt() == 1) {
                game.setWhichPlayer(FigureColor.WHITE);
            } else
                game.setWhichPlayer(FigureColor.BLACK);

            for (int i = 0; i < 64; i++) {

                int position;
                position = (inS.readInt());

                StringBuffer figureColor = new StringBuffer(COLOR_LENGHT);
                for (int j = 0; j < COLOR_LENGHT; j++) {
                    char tChar = inS.readChar();
                    if (tChar != '\0')
                        figureColor.append(tChar);
                }

                StringBuffer figureType = new StringBuffer(TYPE_LENGHT);
                for (int j = 0; j < TYPE_LENGHT; j++) {
                    char tChar = inS.readChar();
                    if (tChar != '\0')
                        figureType.append(tChar);
                }
                game.getPlayer1().setPlayerName(playerName1.toString());
                game.getPlayer2().setPlayerName(playerName2.toString());
                initializeFigure(game.getBoard(), figureColor.toString(), figureType.toString(), position);
            }
            inS.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void initializeFigure(Board board, String figureColor, String figureType, int position) {
        switch (figureType) {
            case "EMPTY": {
                if (figureColor.equals("NONE")) {
                    board.getField(position).setFigure(new Empty());
                }
                break;
            }
            case "PAWN": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Pawn(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Pawn(FigureColor.BLACK));
                    break;
                }
            }
            case "BISHOP": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Bishop(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Bishop(FigureColor.BLACK));
                    break;
                }
            }
            case "KNIGHT": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Knight(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Knight(FigureColor.BLACK));
                    break;
                }
            }
            case "ROOK": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Rook(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Rook(FigureColor.BLACK));
                    break;
                }
            }
            case "QUEEN": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Queen(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Queen(FigureColor.BLACK));
                    break;
                }
            }
            case "KING": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new King(FigureColor.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new King(FigureColor.BLACK));
                    break;
                }
            }
            default:
                break;
        }

    }

    public static void saveGameToFileTxt(Game game, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".txt"));
            writer.println(game.getId());
            writer.println(game.getPlayer1().getPlayerName());
            writer.println(game.getPlayer2().getPlayerName());
            writer.println(game.getWhichPlayer());

            for (int i = 0; i < 64; i++) {
                writer.println(game.getBoard().getField(i).getNumber() + "|" + game.getBoard().getField(i).getCords() + "|" + game.getBoard().getField(i).getFigure().getFigureType() + "|" + game.getBoard().getField(i).getFigure().getFigureColor());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Game loadFileTxtToGame(String fileName) {

        Game game = new Game(InitializeGame.newGame());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));
            int id = Integer.parseInt(reader.readLine());
            game.getPlayer1().setPlayerName(reader.readLine());
            game.getPlayer2().setPlayerName(reader.readLine());

            System.out.println(game.getWhichPlayer());
            game.setWhichPlayer(reader.readLine());
            System.out.println(game.getWhichPlayer());

            for (int i = 0; i < 64; i++) {
                StringTokenizer token = new StringTokenizer(reader.readLine(), "|");
                token.nextToken();
                token.nextToken();


                switch (token.nextToken()) {
                    case "EMPTY": {
                        if (token.nextToken().equals("NONE")) {
                            game.getBoard().getField(i).setFigure(new Empty());
                        }
                        break;

                    }
                    case "PAWN": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new Pawn(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new Pawn(FigureColor.BLACK));
                            break;
                        }
                    }
                    case "BISHOP": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new Bishop(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new Bishop(FigureColor.BLACK));
                            break;
                        }
                    }
                    case "KNIGHT": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new Knight(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new Knight(FigureColor.BLACK));
                            break;
                        }
                    }
                    case "ROOK": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new Rook(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new Rook(FigureColor.BLACK));
                            break;
                        }
                    }
                    case "QUEEN": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new Queen(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new Queen(FigureColor.BLACK));
                            break;
                        }
                    }
                    case "KING": {
                        if (token.nextToken().equals("WHITE")) {
                            game.getBoard().getField(i).setFigure(new King(FigureColor.WHITE));
                            break;
                        } else {
                            game.getBoard().getField(i).setFigure(new King(FigureColor.BLACK));
                            break;
                        }
                    }
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void saveCurrentBoard(Game game) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("board_" + game.getId() + ".txt", true));

            writer.println(moveCounter);
            for (int i = 0; i < 64; i++) {
                writer.println(game.getBoard().getField(i).getFigure().getFigureType() + "|" + game.getBoard().getField(i).getFigure().getFigureColor());
            }
            writer.close();
            moveCounter++;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile() {
        File file = new File("board_1.txt");
        if (file.exists()) {
            file.delete();
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void scanCurrentBoard(Game game) {

        if (Move.getPawnIsMovedOrFigureIsTaking()) {
            deleteFile();
            moveCounter = 0;
            saveCurrentBoard(game);
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("board_" + game.getId() + ".txt"));


                class SimpleBoard extends Object {
                    private String[] figureColor;
                    private String[] figureType;

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

                        if (counter == 64)
                            return true;
                        else
                            return false;
                    }
                }

                SimpleBoard simpleBoard[] = new SimpleBoard[50];

                for (int j = 0; j < moveCounter; j++) {
                    int whichBoard = Integer.parseInt(reader.readLine());
                    simpleBoard[whichBoard] = new SimpleBoard();

                    for (int i = 0; i < 64; i++) {
                        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine(), "|");
                        simpleBoard[whichBoard].setFigureColor(i, stringTokenizer.nextToken());
                        simpleBoard[whichBoard].setFigureType(i, stringTokenizer.nextToken());
                    }
                }

                for (int j = 0; j < moveCounter; j++) {
                    if (j + 1 == moveCounter)
                        break;

                    for (int i = j + 1; i < moveCounter; i++) {
                        if (simpleBoard[j].equals(simpleBoard[i]))
                            theSameBoard++;
                    }
                    if (theSameBoard < 3)
                        theSameBoard = 0;
                    else {
                        game.setDraw(true);
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
