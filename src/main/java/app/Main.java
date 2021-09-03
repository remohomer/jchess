package app;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(InitializeGame.newGame());
        //        Game game = new Game(FileManager.loadFileTxtToGame(1));
        game = new Game(InitializeGame.newGame());
        game.guiGamePlay();
//        game.consoleGamePlay();
    }


}