package fraglab.school.address;

import fraglab.NotFoundException;
import fraglab.school.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseRestController {

    @Autowired
    AddressService addressService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Address fetch(@PathVariable String id) throws NotFoundException {
        return addressService.fetch(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Address address) {
        addressService.update(address);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        addressService.delete(id);
    }


}
