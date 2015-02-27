package fraglab.registry.address;

import fraglab.web.NotFoundException;

public interface AddressService {

    void createOrUpdate(Address address);

    Address fetch(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    boolean isSharedAddress(String addressId);

    Address fetchForChild(String childId);
}
