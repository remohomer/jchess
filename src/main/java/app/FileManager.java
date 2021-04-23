package app;

import enums.Color;
import figures.*;

import java.io.*;
import java.util.StringTokenizer;

public class FileManager {

    private static final int COLOR_LENGHT = 5;
    private static final int TYPE_LENGHT = 5;

    public static void saveBoardToByteFile(Board board, String fileName) {
        try {
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(fileName + ".byte"));

            for (int i = 0; i < 64; i++) {
                outS.writeInt(board.getField(i).getNumber());

                StringBuffer figureColor = new StringBuffer(COLOR_LENGHT);
                figureColor.append(board.getField(i).getFigure().getFigureColor());
                figureColor.setLength(COLOR_LENGHT);
                outS.writeChars(figureColor.toString());

                StringBuffer figureType = new StringBuffer(TYPE_LENGHT);
                figureType.append(board.getField(i).getFigure().getFigureType());
                figureType.setLength(TYPE_LENGHT);
                outS.writeChars(figureType.toString());
            }
            outS.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadByteFileToBoard(Board board, String fileName) {
        try {
            DataInputStream inS = new DataInputStream(new FileInputStream(fileName + ".byte"));

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
                initializeFigureSwitch(board, figureColor.toString(), figureType.toString(), position);
            }
            inS.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void initializeFigureSwitch(Board board, String figureColor, String figureType, int position) {
        switch (figureType) {
            case "EMPTY": {
                if (figureColor.equals("NONE")) {
                    board.getField(position).setFigure(new Empty());
                }
                break;
            }
            case "PAWN": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Pawn(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Pawn(Color.BLACK));
                    break;
                }
            }
            case "BISHOP": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Bishop(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Bishop(Color.BLACK));
                    break;
                }
            }
            case "KNIGHT": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Knight(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Knight(Color.BLACK));
                    break;
                }
            }
            case "ROOK": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Rook(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Rook(Color.BLACK));
                    break;
                }
            }
            case "QUEEN": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new Queen(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new Queen(Color.BLACK));
                    break;
                }
            }
            case "KING": {
                if (figureColor.equals("WHITE")) {
                    board.getField(position).setFigure(new King(Color.WHITE));
                    break;
                } else {
                    board.getField(position).setFigure(new King(Color.BLACK));
                    break;
                }
            }
            default:
                break;
        }

    }

    public static void saveBoardToFileTxt(Board board, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".txt"));
            writer.println(board.getId());

            for (int i = 0; i < 64; i++) {
                writer.println(board.getField(i).getNumber() + "|" + board.getField(i).getCords() + "|" + board.getField(i).getFigure().getFigureType() + "|" + board.getField(i).getFigure().getFigureColor());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Board loadFileTxtToBoard(String fileName) {
        Board board = new Board();
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
                            board.getField(i).setFigure(new Empty());
                        }
                        break;

                    }
                    case "PAWN": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new Pawn(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new Pawn(Color.BLACK));
                            break;
                        }
                    }
                    case "BISHOP": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new Bishop(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new Bishop(Color.BLACK));
                            break;
                        }
                    }
                    case "KNIGHT": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new Knight(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new Knight(Color.BLACK));
                            break;
                        }
                    }
                    case "ROOK": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new Rook(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new Rook(Color.BLACK));
                            break;
                        }
                    }
                    case "QUEEN": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new Queen(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new Queen(Color.BLACK));
                            break;
                        }
                    }
                    case "KING": {
                        if (token.nextToken().equals("WHITE")) {
                            board.getField(i).setFigure(new King(Color.WHITE));
                            break;
                        } else {
                            board.getField(i).setFigure(new King(Color.BLACK));
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
        return board;
    }

}
