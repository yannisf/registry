package fraglab.registry.guardian;

import fraglab.data.GenericDao;
import fraglab.registry.address.AddressService;
import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GuardianServiceImpl implements GuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianServiceImpl.class);

    @Autowired
    GenericDao<Guardian> guardianDao;

    @Autowired
    AddressService addressService;

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        Guardian guardian = fetch(id);
        boolean sharedAddress = addressService.isSharedAddress(guardian.getAddress().getId());
        guardianDao.delete(guardian);
        if (!sharedAddress) {
            addressService.delete(guardian.getAddress().getId());
        }
    }

    @Override
    public void update(Guardian guardian) {
        guardianDao.createOrUpdate(guardian);
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
