package app;

import booleans.CastlingConditions;
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
            DataOutputStream outS = new DataOutputStream(new FileOutputStream("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "game_" + game.getId() + ".txt"));

            outS.writeInt(passiveMoveCounter);

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

    public static Game loadByteFileToGame(int gameId) {

        Figure figure = new Empty();
        Field[] field = new Field[64];

        int row = 8;
        char ch = 97;
        for (int i = 0; i < 64; i++) {
            int column;
            if ((i + 1) % 8 == 0) {
                column = 8;
            } else {
                column = (i + 1) % 8;
            }
            field[i] = new Field(i, row, column, Character.toString(ch) + row, figure);
            if ((i + 1) % 8 == 0) {
                row--;
                ch -= 8;
            }
            ch++;
        }
        CastlingConditions castlingConditions = new CastlingConditions();

        Board board = new Board(castlingConditions, field);
        Player player1 = new Player("Player 1", FigureColor.WHITE);
        Player player2 = new Player("Player 2", FigureColor.BLACK);
        Game game = new Game(board, player1, player2);


        try {
            DataInputStream inS = new DataInputStream(new FileInputStream("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "game_" + gameId + ".txt"));

            passiveMoveCounter = inS.readInt();

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
            PrintWriter writer = new PrintWriter(new FileWriter("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "game_" + game.getId() + ".txt"));
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

    public static Game loadFileTxtToGame(int gameId) {

        Game game = new Game(InitializeGame.newGame());
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "game_" + gameId + ".txt"));
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


                String figureColor = token.nextToken();
                FigureColor newFigureColor;
                if (figureColor.equals("WHITE")) {
                    newFigureColor = FigureColor.WHITE;
                } else {
                    newFigureColor = FigureColor.BLACK;
                }

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
            PrintWriter writer;
            if (append) {
                writer = new PrintWriter(new FileWriter("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "currentBoard_" + game.getId() + ".txt", true));
            } else {
                writer = new PrintWriter(new FileWriter("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "currentBoard_" + game.getId() + ".txt"));
            }

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
            System.out.println("50 ruchów z rzędu bez zbicia figury lub poruszenia pionka... Dokonałeś niemożliwego. \nGratuluje spektakularnego remisu!");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "database" + System.getProperty("file.separator") + "currentBoard_" + game.getId() + ".txt"));

                SimpleBoard simpleBoard[] = new SimpleBoard[50];

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
                    if (j + 1 == passiveMoveCounter)
                        break;

                    for (int i = j + 1; i < passiveMoveCounter; i++) {
                        if (simpleBoard[j].equals(simpleBoard[i]))
                            theSameBoard++;
                    }
                    if (theSameBoard < 3)
                        theSameBoard = 0;
                    else {
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

    public static int getPassiveMoveCounter() {
        return passiveMoveCounter;
    }

    public static void setPassiveMoveCounter(int passiveMoveCounter) {
        FileManager.passiveMoveCounter = passiveMoveCounter;
    }
}
