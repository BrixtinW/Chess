package dataAccess;

public class MemoryAlreadyAllocated extends CustomException {
    public MemoryAlreadyAllocated(String message) {
        super(message);
        this.statusCode = 403;
    }
}
