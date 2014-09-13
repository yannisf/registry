package fraglab.school.child;

import fraglab.school.model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.PUT)
    public void create(@RequestBody Child child) {
        childService.create(child);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        childService.delete(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable Long id) {
        return childService.fetch(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestBody Child child) {
        childService.update(child);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Child> fetchAll() {
        return childService.fetchAll();
    }

}
