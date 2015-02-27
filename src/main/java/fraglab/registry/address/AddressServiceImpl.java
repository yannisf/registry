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
    private GenericDao dao;

    @Override
    public void createOrUpdate(Address address) {
        dao.createOrUpdate(address);
    }

    @Override
    public Address fetch(String id) throws NotFoundException {
        Address address = dao.fetch(Address.class, id);
        if (address == null) {
            throw new NotFoundException("Address not found");
        }
        return address;
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Address address = fetch(id);
        dao.delete(address);
    }

    @Override
    public boolean isSharedAddress(String addressId) {
        String query = "select count(p.address.id) from Person p where p.address.id = :addressId";
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        return dao.countByQuery(query, params) > 1;
    }

    @Override
    public Address fetchForChild(String childId) {
        String query = "select a from Address a where a.id = (select c.address.id from Child c where c.id= :childId)";
        Map<String, Object> params = new HashMap<>();
        params.put("childId", childId);
        return dao.findSingleByQuery(Address.class, query, params);
    }

}
