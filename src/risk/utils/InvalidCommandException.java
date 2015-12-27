package risk.utils;

public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super("Invalid Command!");
    }
    public InvalidCommandException(String message) {
        super(message);
    }
}
