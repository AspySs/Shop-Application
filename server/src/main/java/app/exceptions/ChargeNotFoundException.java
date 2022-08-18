package app.exceptions;

public class ChargeNotFoundException extends RuntimeException{
    public ChargeNotFoundException(String message) {
        super(message);
    }
}
