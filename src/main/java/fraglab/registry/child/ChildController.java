package fraglab.registry.child;

import fraglab.registry.BaseRestController;
import fraglab.registry.formobject.ChildWithAddress;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController extends BaseRestController {

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Child child) throws NotFoundException {
        childService.update(child);
    }

    @RequestMapping(value = "/address", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ChildWithAddress childWithAddress) throws NotFoundException {
        childService.update(childWithAddress);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Child> fetchAll() {
        return childService.fetchAll();
    }

    @RequestMapping(value = "/class/{id}", method = RequestMethod.GET)
    public List<Child> fetchClass(@PathVariable String id) {
        return childService.fetchClass(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        childService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable String id) throws NotFoundException {
        return childService.fetch(id);
    }

}
