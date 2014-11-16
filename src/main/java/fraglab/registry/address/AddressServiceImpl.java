package fraglab.registry.address;

import fraglab.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Address address = fetch(id);
        addressDao.delete(address);
    }

    @Override
    public Address fetch(String id) throws NotFoundException {
        Address address = addressDao.fetch(id);
        if (address == null) {
            throw new NotFoundException("Address not found");
        }

        return address;
    }

    @Override
    public boolean isSharedAddress(String addressId) {
        return addressDao.countAddresses(addressId) > 1;
    }

}
