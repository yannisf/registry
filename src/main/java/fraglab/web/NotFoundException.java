package fraglab.web;

public class NotFoundException extends Exception {

    private String message;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
