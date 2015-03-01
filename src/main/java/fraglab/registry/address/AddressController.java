package fraglab.registry.address;

import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseRestController {

    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestBody Address address) {
        addressService.createOrUpdate(address);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Address fetch(@PathVariable String id) throws NotFoundException {
        return addressService.fetch(id);
    }

    @RequestMapping(value = "child/{childId}", method = RequestMethod.GET)
    public Address fetchForChild(@PathVariable String childId) throws NotFoundException {
        return addressService.fetchForPerson(childId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        addressService.delete(id);
    }


}
