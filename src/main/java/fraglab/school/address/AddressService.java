package fraglab.school.address;

import fraglab.school.Address;

public interface AddressService {

    Address fetch(String id);

    void update(Address address);

    void delete(String id);
}
