package fraglab.school.address;

import fraglab.school.Address;
import fraglab.school.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseRestController {

    @Autowired
    AddressService addressService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Address fetch(@PathVariable Long id) {
        return addressService.fetch(id);
    }

}
