package dataAccess;

public class UnauthorizedRequest extends CustomException {
    public UnauthorizedRequest(String message) {
        super(message);
        this.statusCode = 401;
    }
}
