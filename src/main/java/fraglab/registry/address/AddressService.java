package fraglab.registry.address;

import fraglab.web.NotFoundException;

public interface AddressService {

    void save(Address address);

    Address find(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    Address findForPerson(String childId);
}
