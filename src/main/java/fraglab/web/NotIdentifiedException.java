package fraglab.web;

public class NotIdentifiedException extends RuntimeException {

    private String message;

    public NotIdentifiedException() {
    }

    public NotIdentifiedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
