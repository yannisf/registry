package fraglab.web;

import java.io.Serializable;

public class ControllerErrorWrapper implements Serializable {

    private String description;
    private String internalMessage;

    public ControllerErrorWrapper() { }

    public ControllerErrorWrapper(String description, String internalMessage) {
        this.description = description;
        this.internalMessage = internalMessage;
    }

    public String getDescription() {
        return description;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

}
