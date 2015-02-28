package fraglab.registry.child;

import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/child")
public class ChildController extends BaseRestController {

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestBody Child child, @RequestParam("addressId") String addressId, 
                               @RequestParam("groupId") String groupId) 
            throws NotIdentifiedException, NotFoundException {
        childService.createOrUpdate(child, addressId, groupId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable String id) throws NotFoundException {
        return childService.fetch(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        childService.delete(id);
    }

}
