package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController extends BaseRestController {

    @Autowired
    GuardianService guardianService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Guardian guardian) {
        guardianService.create(guardian);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Guardian guardian) throws NotFoundException {
        guardianService.update(id, guardian);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        guardianService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Guardian fetch(@PathVariable Long id) throws NotFoundException {
        return guardianService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Guardian> fetchAll() {
        return guardianService.fetchAll();
    }

}
