package fraglab.registry.guardian;

import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardian")
public class GuardianController extends BaseRestController {

    @Autowired
    private GuardianService guardianService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Guardian find(@PathVariable String id) throws NotFoundException {
        return guardianService.find(id).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody Guardian guardian, @RequestParam("addressId") String addressId)
            throws NotIdentifiedException {
        guardianService.save(guardian, addressId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        guardianService.delete(id);
    }

}
