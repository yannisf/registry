package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.address.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GuardianServiceImpl implements GuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianServiceImpl.class);

    @Autowired
    GuardianDao guardianDao;

    @Autowired
    AddressService addressService;

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        Guardian guardian = fetch(id);
        boolean sharedAddress = addressService.isSharedAddress(guardian.getAddressId());
        guardianDao.delete(guardian);
        if (!sharedAddress) {
            addressService.delete(guardian.getAddressId());
        }
    }

    @Override
    public void update(Guardian guardian) {
        guardianDao.update(guardian);
    }

    @Override
    public Guardian fetch(String id) throws NotFoundException {
        Guardian guardian = guardianDao.fetch(id);
        if (guardian == null) {
            throw new NotFoundException("Guardian not found");
        }

        return guardian;
    }

}
