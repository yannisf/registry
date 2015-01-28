package fraglab.registry;

import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseRestController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseRestController.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ControllerErrorWrapper handleNotFoundException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Resource not found", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ControllerErrorWrapper handleException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Bad request", e.getMessage());
    }

}
