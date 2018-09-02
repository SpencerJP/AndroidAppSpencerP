package rmit.s3539519.madassignment1.model;

// simple exception for display toast error messages
public class TrackingNotValidException extends Exception {

    private String message;
    public TrackingNotValidException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
