package app;

import enums.FigureColor;
import figures.*;

import java.io.*;
import java.util.StringTokenizer;

public class FileManager {

    private static final int NAME_LENGHT = 30;
    private static final int COLOR_LENGHT = 5;
    private static final int TYPE_LENGHT = 6;
    private static int passiveMoveCounter = 0;
    private static int theSameBoard = 0;

    public static void saveGameToByteFile(Game game) {
        try {
            String patch = patchBuilder(game.getId(), "game");
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(patch));

            outS.writeInt(passiveMoveCounter);

            StringBuilder firstPlayerName = new StringBuilder((NAME_LENGHT));
            firstPlayerName.append(game.getPlayer1().getPlayerName());
            firstPlayerName.setLength(NAME_LENGHT);
            outS.writeChars(firstPlayerName.toString());

            StringBuilder secondPlayerName = new StringBuilder((NAME_LENGHT));
            secondPlayerName.append(game.getPlayer2().getPlayerName());
            secondPlayerName.setLength(NAME_LENGHT);
            outS.writeChars(secondPlayerName.toString());

            if (game.getWhichPlayer() == FigureColor.WHITE) {
                outS.writeInt(1);
            } else {
                outS.writeInt(2);
            }

            for (int i = 0; i < 64; i++) {
                outS.writeInt(game.getBoard().getField(i).getNumber());

                StringBuilder figureColor = new StringBuilder(COLOR_LENGHT);
                figureColor.append(game.getBoard().getField(i).getFigure().getFigureColor());
                figureColor.setLength(COLOR_LENGHT);
                outS.writeChars(figureColor.toString());

                StringBuilder figureType = new StringBuilder(TYPE_LENGHT);
                figureType.append(game.getBoard().getField(i).getFigure().getFigureType());
                figureType.setLength(TYPE_LENGHT);
                outS.writeChars(figureType.toString());
            }
            outS.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Game loadByteFileToGame(int gameId) {

        Game game = new Game(InitializeGame.newGame());

        try {
            String patch = patchBuilder(game.getId(), "game");
            DataInputStream inS = new DataInputStream(new FileInputStream(patch));

            passiveMoveCounter = inS.readInt();

            StringBuilder firstPlayerName = new StringBuilder(stringBuilderFactory(inS, NAME_LENGHT));
            StringBuilder secondPlayerName = new StringBuilder(stringBuilderFactory(inS, NAME_LENGHT));

            if (inS.readInt() == 1) {
                game.setWhichPlayer(FigureColor.WHITE);
            } else
                game.setWhichPlayer(FigureColor.BLACK);

            for (int i = 0; i < 64; i++) {

                int position;
                position = (inS.readInt());

                String figureColor = new String(stringBuilderFactory(inS, COLOR_LENGHT));
                String figureType = new String(stringBuilderFactory(inS, TYPE_LENGHT));

                game.getPlayer1().setPlayerName(firstPlayerName.toString());
                game.getPlayer2().setPlayerName(secondPlayerName.toString());
                initializeFigure(game.getBoard(), figureColor, figureType, position);
            }
            inS.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void initializeFigure(Board board, String figureColor, String figureType, int position) {

        FigureColor newFigureColor;
        if (figureColor.equals("WHITE")) {
            newFigureColor = FigureColor.WHITE;
        } else {
            newFigureColor = FigureColor.BLACK;
        }

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

    public static void saveGameToFileTxt(Game game) {

        try {
            String patch = patchBuilder(game.getId(), "game");
            PrintWriter writer = new PrintWriter(new FileWriter(patch));

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

    public static Game loadFileTxtToGame(int gameId) {

        Game game = new Game(InitializeGame.newTempGame());
        try {
            String patch = patchBuilder(game.getId(), "game");
            BufferedReader reader = new BufferedReader(new FileReader(patch));
            game.getPlayer1().setPlayerName(reader.readLine());
            game.getPlayer2().setPlayerName(reader.readLine());

            game.setWhichPlayer(reader.readLine());

            for (int i = 0; i < 64; i++) {
                StringTokenizer token = new StringTokenizer(reader.readLine(), "|");
                token.nextToken();
                token.nextToken();

                String figureColor = token.nextToken();
                FigureColor newFigureColor = figureColor.equals("WHITE") ? FigureColor.WHITE : FigureColor.BLACK;

                switch (figureColor) {
                    case "EMPTY": {
                        game.getBoard().getField(i).setFigure(new Empty());
                        break;
                    }
                    case "PAWN": {
                        game.getBoard().getField(i).setFigure(new Pawn(newFigureColor));
                        break;
                    }
                    case "BISHOP": {
                        game.getBoard().getField(i).setFigure(new Bishop(newFigureColor));
                        break;
                    }
                    case "KNIGHT": {
                        game.getBoard().getField(i).setFigure(new Knight(newFigureColor));
                        break;
                    }
                    case "ROOK": {
                        game.getBoard().getField(i).setFigure(new Rook(newFigureColor));
                        break;
                    }
                    case "QUEEN": {
                        game.getBoard().getField(i).setFigure(new Queen(newFigureColor));
                        break;
                    }
                    case "KING": {
                        game.getBoard().getField(i).setFigure(new King(newFigureColor));
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void saveCurrentBoard(Game game, boolean append) {

        try {
            String patch = patchBuilder(game.getId(), "board");
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
        File file = new File("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "currentBoard_" + game.getId() + ".txt");
        if (file.exists()) {
            file.delete();
        }
    }


    public static void scanCurrentBoard(Game game) {

        if (passiveMoveCounter > 50) {
            game.setDraw(true);
            System.out.println("50 ruchów z rzędu bez zbicia figury lub poruszenia pionka... \nGratuluje spektakularnego remisu!");
        } else {
            try {
                String patch = patchBuilder(game.getId(), "board");
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
                        System.out.println("50 ruchów z rzędu bez zbicia figury lub poruszenia pionka... Dokonałeś niemożliwego. \nGratuluje spektakularnego remisu!");
                        break;
                    }
                }
                reader.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String patchBuilder(int gameId, String name) {
        StringBuilder patch = new StringBuilder();

        patch.append("src");
        patch.append(System.getProperty("file.separator"));
        patch.append("main");
        patch.append(System.getProperty("file.separator"));
        patch.append("java");
        patch.append(System.getProperty("file.separator"));
        patch.append("database");
        patch.append(System.getProperty("file.separator"));
        patch.append(name + "_" + gameId + ".txt");

        return patch.toString();
    }

    public static StringBuilder stringBuilderFactory(DataInputStream inS, int LENGHT) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(LENGHT);
        for (int j = 0; j < LENGHT; j++) {
            char tChar = inS.readChar();
            if (tChar != '\0') {
                stringBuilder.append(tChar);
            }
        }
        stringBuilder.setLength(LENGHT);
        return stringBuilder;
    }

    public static void setPassiveMoveCounter(int passiveMoveCounter) {
        FileManager.passiveMoveCounter = passiveMoveCounter;
    }
}
