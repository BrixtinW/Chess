package dataAccess.Exceptions;

public class CustomException extends Exception {

    public int statusCode;

    public CustomException(String message) {
        super(message);
    }

}
