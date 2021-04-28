package app;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(InitializeGame.loadGame("1"));
//        Game game = new Game(InitializeGame.newGame("Adam","Leszek"));
        game.start();


    }
}