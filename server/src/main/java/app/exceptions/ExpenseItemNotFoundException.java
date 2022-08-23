package app.exceptions;

public class ExpenseItemNotFoundException extends RuntimeException {
    public ExpenseItemNotFoundException(String message) {
        super(message);
    }
}
