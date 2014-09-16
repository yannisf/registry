package fraglab.school.grownup;

import fraglab.NotFoundException;
import fraglab.school.ControllerErrorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grownup")
public class GrownupController {

    private static final Logger LOG = LoggerFactory.getLogger(GrownupController.class);

    @Autowired
    GrownupService grownupService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Grownup grownup) {
        grownupService.create(grownup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Grownup grownup) throws NotFoundException {
        grownupService.update(id, grownup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        grownupService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Grownup fetch(@PathVariable Long id) throws NotFoundException {
        return grownupService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Grownup> fetchAll() {
        return grownupService.fetchAll();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ControllerErrorWrapper handleNotFoundException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Resource not found", e.getMessage());
    }

}
