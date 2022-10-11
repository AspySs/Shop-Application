package app.exceptions;

public class InvalidJwtAuthentificationException extends RuntimeException {
    public InvalidJwtAuthentificationException(String message) {
        super(message);
    }
}
