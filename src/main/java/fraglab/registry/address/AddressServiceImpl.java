package fraglab.registry.address;

import fraglab.data.GenericDao;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private GenericDao<Address, String> addressDao;

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

    public Long countAddresses(String addressId) {
        Query query = entityManager.createQuery("select count(p.addressId) from Person p where p.addressId = :addressId");
        return (Long) query.setParameter("addressId", addressId).getSingleResult();
    }


    @Override
    public boolean isSharedAddress(String addressId) {
        
        return addressDao.countAddresses(addressId) > 1;
    }

}
