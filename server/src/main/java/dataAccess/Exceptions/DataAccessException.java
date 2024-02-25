package dataAccess.Exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends CustomException {
    public DataAccessException(String message) {
        super(message);
        this.statusCode = 500;
    }
}
