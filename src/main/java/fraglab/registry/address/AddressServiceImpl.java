package fraglab.registry.address;

import fraglab.data.GenericDao;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private GenericDao<Address> addressDao;

    @Override
    public void createOrUpdate(Address address) {
        addressDao.createOrUpdate(address);
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
    public void delete(String id) throws NotFoundException {
        Address address = fetch(id);
        addressDao.delete(address);
    }

    @Override
    public boolean isSharedAddress(String addressId) {
        String query = "select count(p.address.id) from Person p where p.address.id = :addressId";
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        return addressDao.countByQuery(query, params) > 1;
    }

}
