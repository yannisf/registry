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
    public Address fetchForPerson(String personId) {
        String query = "select a from Address a where a.id = (select p.address.id from Person p where p.id= :personId)";
        Map<String, Object> params = new HashMap<>();
        params.put("personId", personId);
        return dao.findSingleByQuery(Address.class, query, params);
    }

}
