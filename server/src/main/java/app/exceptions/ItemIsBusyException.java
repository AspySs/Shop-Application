package app.exceptions;

public class ItemIsBusyException extends RuntimeException{
    public ItemIsBusyException(String message) {
        super(message);
    }
}