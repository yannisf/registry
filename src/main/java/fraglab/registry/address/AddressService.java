package fraglab.registry.address;

import fraglab.NotFoundException;

public interface AddressService {

    void update(Address address);

    void delete(String id) throws NotFoundException;

    Address fetch(String id) throws NotFoundException;

    boolean isSharedAddress(String addressId);
}
