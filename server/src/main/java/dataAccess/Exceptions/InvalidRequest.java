package dataAccess.Exceptions;

public class InvalidRequest extends CustomException {
    public InvalidRequest(String message) {
        super(message);
        this.statusCode = 400;
    }
}
