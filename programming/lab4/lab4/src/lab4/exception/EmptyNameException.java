package lab4.exception;

public class EmptyNameException extends RuntimeException{
    public EmptyNameException(String message) {
        super(message);
    }
    public EmptyNameException() {
        super();
    }
}