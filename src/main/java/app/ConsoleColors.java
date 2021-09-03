package app;

public interface ConsoleColors {

    String  RESET_COLORS = "\033[0m";                // Colors Reset

    String WHITE_FIGURE = "\033[1;97m";             // WHITE
    String BLACK_FIGURE = "\033[1;30m";             // BLACK
    String EMPTY_FIGURE = "\033[1;37m";             // WHITE

    String BG_LEGAL_OFFENSIVE_MOVE = "\033[41m";    // RED
    String BG_LEGAL_MOVE = "\033[42m";              // GREEN
    String BG_SELECTED_COLOR = "\033[43m";          // YELLOW
    String BG_COLOR_1 = "\033[44m";                 // BLUE
    String BG_COLOR_2 = "\033[0;106m";              // CYAN
    String BG_CURRENT_PLAYERS_TURN = "\033[0;104m"; // BLUE
}



