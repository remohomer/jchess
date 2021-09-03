package enums;

public enum Error {
    INCORRECT_TYPE_OF_BOARD (0,"incorrect BoardType"),
    INCORRECT_TYPE_OF_FIGURE (1, "incorrect FigureType"),
    THE_SAME_OBJECT (2, "you can not compare the same object"),
    OBJECT_IS_NULL (3, "object does not exist"),
    INCORRECT_TYPE_OF_OBJECT (4, "object class is incorrect"),
    INCORRECT_INPUT_DATA (5, "incorrect input data");

    private final String message;

    Error(int id, String message) {
        this.message = "ERROR " + id + ": " + message;
    }

    public String getMessage() {
        return message;
    }
}
