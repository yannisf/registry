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
    public Address fetch(Long id) {
        return addressDao.fetch(id);
    }
}
