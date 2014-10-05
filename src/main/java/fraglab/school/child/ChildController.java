package fraglab.school.child;

import fraglab.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Child create(@RequestBody Child child) {
        return childService.create(child);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Child child) throws NotFoundException {
        childService.update(id, child);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        childService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable Long id) throws NotFoundException {
        return childService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Child> fetchAll() {
        return childService.fetchAll();
    }

}
