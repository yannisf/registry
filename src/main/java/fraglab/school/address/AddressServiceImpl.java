package fraglab.school.address;

import fraglab.school.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address fetch(String id) {
        return addressDao.fetch(id);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void delete(String id) {
        Address address = fetch(id);
        addressDao.delete(address);
    }
}
