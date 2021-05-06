package app;

import enums.FigureColor;
import figures.*;

import java.io.*;
import java.util.StringTokenizer;

public class FileManager {

    private static final int NAME_LENGTH = 30;
    private static final int COLOR_LENGTH = 5;
    private static final int TYPE_LENGTH = 6;
    private static int passiveMoveCounter = 0;
    private static int theSameBoard = 0;

    public static void saveGameToDataFile(Game game) {
        try {
            String patch = patchBuilder(game.getId(), "game","dat");
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(patch));

            buildStringAndWriteToFile(outS, game.getPlayer1().getPlayerName(), NAME_LENGTH);
            buildStringAndWriteToFile(outS, game.getPlayer2().getPlayerName(), NAME_LENGTH);

            if (game.getWhoseTurn() == FigureColor.WHITE) {
                outS.writeInt(1);
            } else {
                outS.writeInt(2);
            }

            for (int i = 0; i < 64; i++) {
                outS.writeInt(game.getBoard().getField(i).getNumber());
                buildStringAndWriteToFile(outS, game.getBoard().getField(i).getFigure().getFigureColor().toString(), COLOR_LENGTH);
                buildStringAndWriteToFile(outS, game.getBoard().getField(i).getFigure().getFigureType().toString(), TYPE_LENGTH);
            }
            outS.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Game loadDataFileToGame(int gameId) {

        Game game = new Game(InitializeGame.newGame());

        try {
            String patch = patchBuilder(gameId, "game","dat");
            DataInputStream inS = new DataInputStream(new FileInputStream(patch));

            String firstPlayerName = convertCharsToString(inS, NAME_LENGTH);
            String secondPlayerName = convertCharsToString(inS, NAME_LENGTH);

            if (inS.readInt() == 1) {
                game.setWhoseTurn(FigureColor.WHITE);
            } else
                game.setWhoseTurn(FigureColor.BLACK);

            for (int i = 0; i < 64; i++) {

                int position;
                position = (inS.readInt());

                String figureColor = convertCharsToString(inS, COLOR_LENGTH);
                String figureType = convertCharsToString(inS, TYPE_LENGTH);

                game.getPlayer1().setPlayerName(firstPlayerName);
                game.getPlayer2().setPlayerName(secondPlayerName);
                initializeFigure(game.getBoard(), figureColor, figureType, position);
            }
            inS.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void saveGameToFileTxt(Game game) {

        try {
            String patch = patchBuilder(game.getId(), "game","txt");
            PrintWriter writer = new PrintWriter(new FileWriter(patch));

            writer.println(game.getPlayer1().getPlayerName());
            writer.println(game.getPlayer2().getPlayerName());
            writer.println(game.getWhoseTurn());

            for (int i = 0; i < 64; i++) {
                writer.println(game.getBoard().getField(i).getNumber() + "|" + game.getBoard().getField(i).getFigure().getFigureType() + "|" + game.getBoard().getField(i).getFigure().getFigureColor());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Game loadFileTxtToGame(int gameId) {

        Game game = new Game(InitializeGame.newTempGame());
        try {
            String patch = patchBuilder(gameId, "game","txt");
            BufferedReader reader = new BufferedReader(new FileReader(patch));

            game.getPlayer1().setPlayerName(reader.readLine());
            game.getPlayer2().setPlayerName(reader.readLine());
            game.setWhoseTurn(reader.readLine());

            for (int i = 0; i < 64; i++) {
                StringTokenizer token = new StringTokenizer(reader.readLine(), "|");
                int position = Integer.parseInt(token.nextToken());

                String figureType = token.nextToken();
                String figureColor = token.nextToken();
                initializeFigure(game.getBoard(), figureColor, figureType, position);

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void initializeFigure(Board board, String figureColor, String figureType, int position) {

        FigureColor newFigureColor = figureColor.equals("WHITE") ? FigureColor.WHITE : FigureColor.BLACK;

        switch (figureType) {
            case "EMPTY": {
                board.getField(position).setFigure(new Empty());
                break;
            }
            case "PAWN": {
                board.getField(position).setFigure(new Pawn(newFigureColor));
                break;
            }
            case "BISHOP": {
                board.getField(position).setFigure(new Bishop(newFigureColor));
                break;
            }
            case "KNIGHT": {
                board.getField(position).setFigure(new Knight(newFigureColor));
                break;
            }
            case "ROOK": {
                board.getField(position).setFigure(new Rook(newFigureColor));
                break;
            }
            case "QUEEN": {
                board.getField(position).setFigure(new Queen(newFigureColor));
                break;
            }
            case "KING": {
                board.getField(position).setFigure(new King(newFigureColor));
                break;
            }
        }
    }

    public static void saveCurrentBoard(Game game, boolean append) {

        try {
            String patch = patchBuilder(game.getId(), "board","txt");
            PrintWriter writer = append ?
                    new PrintWriter(new FileWriter(patch, true)) :
                    new PrintWriter(new FileWriter(patch));

            writer.println(passiveMoveCounter);
            for (int i = 0; i < 64; i++) {
                writer.println(game.getBoard().getField(i).getFigure().getFigureType() + "|" + game.getBoard().getField(i).getFigure().getFigureColor());
            }
            writer.close();
            passiveMoveCounter++;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCurrentBoardFile(Game game) {
        String patch = patchBuilder(game.getId(), "board","txt");
        File file = new File(patch);
        if (file.exists()) {
            file.delete();
        }
    }


    public static void scanCurrentBoard(Game game) {

        if (passiveMoveCounter > 50) {
            game.setDraw(true);
            System.out.println("50 moves in a row without capturing a piece or moving a pawn ...\n" +
                    "Congratulations on a spectacular draw!");
        } else {
            try {
                String patch = patchBuilder(game.getId(), "board","txt");
                BufferedReader reader = new BufferedReader(new FileReader(patch));

                SimpleBoard[] simpleBoard = new SimpleBoard[50];

                for (int j = 0; j < passiveMoveCounter; j++) {
                    int whichBoard = Integer.parseInt(reader.readLine());
                    simpleBoard[whichBoard] = new SimpleBoard();

                    for (int i = 0; i < 64; i++) {
                        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine(), "|");
                        simpleBoard[whichBoard].setFigureColor(i, stringTokenizer.nextToken());
                        simpleBoard[whichBoard].setFigureType(i, stringTokenizer.nextToken());
                    }
                }

                for (int j = 0; j < passiveMoveCounter; j++) {
                    if (j + 1 == passiveMoveCounter) {
                        break;
                    }

                    for (int i = j + 1; i < passiveMoveCounter; i++) {
                        if (simpleBoard[j].equals(simpleBoard[i]))
                            theSameBoard++;
                    }

                    if (theSameBoard < 3) {
                        theSameBoard = 0;
                    } else {
                        game.setDraw(true);
                        System.out.println("IS A DRAW!");
                        break;
                    }
                }
                reader.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String patchBuilder(int gameId, String name, String extension) {
        final String SEP = System.getProperty("file.separator");
        return "src" + SEP + "main" + SEP + "java" + SEP + "database" + SEP + name + "_" + gameId + "." + extension;
    }

    public static void buildStringAndWriteToFile(DataOutputStream outS, String append, int LENGTH) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(LENGTH).append(append);
        stringBuilder.setLength(LENGTH);
        outS.writeChars(stringBuilder.toString());
    }

    public static String convertCharsToString(DataInputStream inS, int LENGTH) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(LENGTH);
        for (int j = 0; j < LENGTH; j++) {
            char tChar = inS.readChar();
            if (tChar != '\0') {
                stringBuilder.append(tChar);
            }
        }
        return stringBuilder.toString();
    }

    public static void setPassiveMoveCounter(int passiveMoveCounter) {
        FileManager.passiveMoveCounter = passiveMoveCounter;
    }
}
