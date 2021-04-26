package app;

import enums.FigureColor;
import figures.*;

import java.io.*;
import java.util.StringTokenizer;

public class FileManager {

    private static final int COLOR_LENGHT = 5;
    private static final int TYPE_LENGHT = 5;

    public static void saveGameToByteFile(Game game, String fileName) {
        try {
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(fileName + ".txt"));

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

    public static void loadByteFileToGame(Game game, String fileName) {
        try {
            DataInputStream inS = new DataInputStream(new FileInputStream(fileName + ".txt"));

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
                initializeFigure(game.getBoard(), figureColor.toString(), figureType.toString(), position);
            }
            inS.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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

    public static void saveBoardToFileTxt(Game game, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".txt"));
            writer.println(game.getBoard().getId());

            for (int i = 0; i < 64; i++) {
                writer.println(game.getBoard().getField(i).getNumber() + "|" + game.getBoard().getField(i).getCords() + "|" + game.getBoard().getField(i).getFigure().getFigureType() + "|" + game.getBoard().getField(i).getFigure().getFigureColor());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadFileTxtToGame(Game game, String fileName) {

        game = new Game(InitializeGame.start());


        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));
            int id = Integer.parseInt(reader.readLine());

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
    }

}
