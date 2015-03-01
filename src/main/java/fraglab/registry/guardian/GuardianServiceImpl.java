package fraglab.registry.guardian;

import fraglab.data.GenericDao;
import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.foundation.Group;
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
    GenericDao dao;

    @Autowired
    AddressService addressService;

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        Guardian guardian = fetch(id);
        boolean sharedAddress = addressService.isSharedAddress(guardian.getAddress().getId());
        dao.delete(guardian);
        if (!sharedAddress) {
            addressService.delete(guardian.getAddress().getId());
        }
    }

    @Override
    public void createOrUpdate(Guardian guardian) {
        dao.createOrUpdate(guardian);
    }

    @Override
    public void createOrUpdate(Guardian guardian, String addressId) {
        Address address = dao.fetch(Address.class, addressId);
        guardian.setAddress(address);
        createOrUpdate(guardian);
    }

    @Override
    public Guardian fetch(String id) throws NotFoundException {
        Guardian guardian = dao.fetch(Guardian.class, id);
        if (guardian == null) {
            throw new NotFoundException("Guardian not found");
        }

        return guardian;
    }

}
