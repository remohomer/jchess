package gui;

import app.FileManager;
import app.Game;
import app.Move;
import app.Printer;
import enums.FigureColor;
import enums.FigureType;
import enums.PrintBoardType;
import app.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final Color lightFieldColor = Color.decode("#eeedd4");
    private final Color darkFieldColor = Color.decode("#76945b");
    private final Color selectedFieldColor = Color.decode("#e2de79");
    private final Color legalFieldColor = Color.decode("#f05456");
    private final Color legalEmptyFieldColor = Color.decode("#f48183");
    private final Color lastMoveSourceColor = Color.decode("#f8f476");
    private final Color lastMoveDestinyColor = Color.decode("#f8f476");

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    public static Dimension FRAME_DIMENSION = new Dimension(500, 500);
    public static Dimension BOARD_PANEL_DIMENSION = new Dimension(450, 450);
    public static Dimension FIELD_PANEL_DIMENSION = new Dimension(10, 10);
    private static String defaultFigureIconPath = "src/main/java/images/figures/png/";

    public Table(Game game) {
        this.gameFrame = new JFrame("jChess by Remoh");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar gameMenuBar = createMenuBar(game);
        this.gameFrame.setJMenuBar(gameMenuBar);
        this.gameFrame.setSize(FRAME_DIMENSION);
        this.boardPanel = new BoardPanel(game);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
        this.gameFrame.setResizable(false);

    }

    private JMenuBar createMenuBar(Game game) {
        final JMenuBar gameMenuBar = new JMenuBar();
        gameMenuBar.add(createGameFileMenu(game));
        return gameMenuBar;
    }

    private JMenu createGameFileMenu(Game game) {
        final JMenu fileMenu = new JMenu("Gra");

        final JMenuItem newGame = new JMenuItem("Nowa gra");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.newGame();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(game);
                    }
                });
                System.out.println("new game!");
            }
        });
        fileMenu.add(newGame);

        final JMenuItem loadGame = new JMenuItem("Wczytaj ostatnią gre");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                game.loadGame(1);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(game);
                    }
                });
                System.out.println("Load last game from: game_" + game.getId() + ".txt");

            }
        });
        fileMenu.add(loadGame);

        final JMenuItem stepBack = new JMenuItem("Cofnij ostatni ruch");
        stepBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.loadLastMove(666);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(game);
                    }
                });
                System.out.println("Cofam ostatni ruch");
            }
        });
        fileMenu.add(stepBack);

        final JMenuItem saveGame = new JMenuItem("Zapisz i wyjdź");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileManager.saveGameToFileTxt(game);
                FileManager.saveGameToDataFile(game);
                System.out.println("save game to game_" + game.getId() + ".txt");
                System.out.println("save game to game_" + game.getId() + ".dat");
                System.out.println("exit game");
                System.exit(0);
            }
        });
        fileMenu.add(saveGame);

        final JMenuItem exitMenuItem = new JMenuItem("Wyjdź");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final List<FieldPanel> boardFields;

        BoardPanel(Game game) {
            super(new GridLayout(8, 8));
            this.boardFields = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                final FieldPanel fieldPanel = new FieldPanel(this, i, game);
                this.boardFields.add(fieldPanel);
                add(fieldPanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Game game) {
            removeAll();
            for (final FieldPanel fieldPanel : boardFields) {
                fieldPanel.drawField(game);
                add(fieldPanel);
            }
            validate();
            repaint();
        }


        private class FieldPanel extends JPanel {
            private final int fieldId;

            FieldPanel(BoardPanel boardPanel, final int fieldId, Game game) {
                super(new GridLayout());
                this.fieldId = fieldId;
                setPreferredSize(FIELD_PANEL_DIMENSION);
                assignFieldColor(game);
                assignFieldFigureIcon(game);

                addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {

                        if (isRightMouseButton(e)) {
                            game.sourcePosition = -1;
                            game.destinyPosition = -1;
                            Move.clearSelectedFigureAndLegalMoves(game.getBoard());
                            redrawFields(game);
                            System.out.println("Puściłem figure");
                        } else if (isLeftMouseButton(e)) {
                            if (game.sourcePosition == -1) {
                                if (Move.isLegalFirstPosition(game, game.getWhoseTurn(), fieldId)) {
                                    game.sourcePosition = fieldId;
                                    game.getBoard().getField(fieldId).getFigure().setLegalMovement(game, game.sourcePosition);
                                    redrawFields(game);
                                    System.out.println("Złapałem nową figure!");
                                    Printer.printBoard(game, fieldId, PrintBoardType.CHECK_LINES);
                                    Printer.printBoard(game, fieldId, PrintBoardType.PINNED_AND_PINNED_CHECK_LINES);
//                                    Printer.printBoard(game, fieldId, PrintBoardType.NUMBERS);
                                    Printer.printBooleans(game);
                                }
                            } else {
                                if (Move.isLegalSecondPosition(game, game.getWhoseTurn(), fieldId)) {
                                    game.destinyPosition = fieldId;
                                    Move.clearFigureStates(game);
                                    Move.move(game, game.sourcePosition, game.destinyPosition);
                                    game.sourcePosition = -1;
                                    game.destinyPosition = -1;
                                    Move.clearSelectedFigureAndLegalMoves(game.getBoard());
                                    game.lookingForCheckMateOrDraw(); // w środku jest invertWhosePlayer();
                                    game.setLegalMovesForCurrentPlayer();
                                    redrawFields(game);
                                    System.out.println("Wykonałem poprawny ruch");
                                    Printer.printBoard(game, Printer.NOT_SELECTED_FIGURE, PrintBoardType.CHECK_LINES);
                                    Printer.printBoard(game, Printer.NOT_SELECTED_FIGURE, PrintBoardType.PINNED_AND_PINNED_CHECK_LINES);
//                                    Printer.printBoard(game, fieldId, PrintBoardType.NUMBERS);
                                    Printer.printBooleans(game);
                                } else {
                                    if (Move.isLegalFirstPosition(game, game.getWhoseTurn(), fieldId)) {
                                        Move.clearSelectedFigureAndLegalMoves(game.getBoard());
                                        game.sourcePosition = fieldId;
                                        game.getBoard().getField(fieldId).getFigure().setLegalMovement(game, game.sourcePosition);
                                        redrawFields(game);
                                        System.out.println("Zmieniłem złapaną figure!");
                                        Printer.printBoard(game, fieldId, PrintBoardType.CHECK_LINES);
                                        Printer.printBoard(game, fieldId, PrintBoardType.PINNED_AND_PINNED_CHECK_LINES);
//                                        Printer.printBoard(game, fieldId, PrintBoardType.NUMBERS);
                                        Printer.printBooleans(game);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(final MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(final MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(final MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(final MouseEvent e) {

                    }
                });

                validate();
            }

            public void redrawFields(final Game game) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(game);
                    }
                });
            }

            public void drawField(final Game game) {
                assignFieldColor(game);
                assignFieldFigureIcon(game);
                validate();
                repaint();
            }

            private void assignFieldFigureIcon(final Game game) {
                this.removeAll();
                if (game.getBoard().getField(fieldId).getFigure().getFigureType() != FigureType.EMPTY) {
                    try {
                        final BufferedImage image =
                                ImageIO.read(new File(defaultFigureIconPath +
                                        game.getBoard().getField(fieldId).getFigure().getFigureColor().toString() +
                                        "_" +
                                        game.getBoard().getField(fieldId).getFigure().getFigureType().toString() +
                                        ".png"));
                        add(new JLabel(new ImageIcon(image)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void assignFieldColor(Game game) {

                if (game.getBoard().getField(fieldId).getFigure().isSelected()) {
                    setBackground(selectedFieldColor);
                } else if (game.getBoard().getField(fieldId).getFigure().isLegalMove()) {
                    if (game.getWhoseTurn() == FigureColor.BLACK && game.getBoard().getField(fieldId).getFigure().isEnPassantForBlack()) {
                        setBackground(legalFieldColor);
                    } else if (game.getWhoseTurn() == FigureColor.WHITE && game.getBoard().getField(fieldId).getFigure().isEnPassantForWhite()) {
                        setBackground(legalFieldColor);
                    } else if (game.getBoard().getField(fieldId).getFigure().getFigureType() == FigureType.EMPTY) {
                        setBackground(legalEmptyFieldColor);
                    } else {
                        setBackground(legalFieldColor);
                    }
                } else if (fieldId == Move.lastMoveSource) {
                    setBackground(lastMoveSourceColor);
                } else if (fieldId == Move.lastMoveDestiny) {
                    setBackground(lastMoveDestinyColor);
                } else {
                    setBackground(game.getBoard().getField(fieldId).getRow() % 2 == 0
                            ? (fieldId % 2 == 0) ? lightFieldColor : darkFieldColor
                            : (fieldId % 2 == 0) ? darkFieldColor : lightFieldColor);
                }
            }
        }
    }
}
