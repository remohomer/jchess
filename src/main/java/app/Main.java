package app;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(InitializeGame.start("Player 1", "Player 2"));
        game.newGame();

    }
}