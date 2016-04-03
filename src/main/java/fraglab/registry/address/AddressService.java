package fraglab.registry.address;

import java.util.Optional;

public interface AddressService {

    Optional<Address> find(String id);

    Address save(Address address);

    void delete(String id);

    Address findForPerson(String childId);
}
