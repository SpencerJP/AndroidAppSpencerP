package rmit.s3539519.madassignment1.model;

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
