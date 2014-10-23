package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardian")
public class GuardianController extends BaseRestController {

    @Autowired
    GuardianService guardianService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Guardian guardian) throws NotFoundException {
        guardianService.update(guardian);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        guardianService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Guardian fetch(@PathVariable String id) throws NotFoundException {
        return guardianService.fetch(id);
    }

}
