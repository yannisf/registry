package fraglab.registry.guardian;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GuardianServiceImpl implements GuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianServiceImpl.class);

    @Autowired
    private  GuardianJpaRepository guardianJpaRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public void delete(String id) throws NotFoundException {
        Guardian guardian = find(id);
        guardianJpaRepository.delete(guardian);
        addressService.delete(guardian.getAddress().getId());
    }

    @Override
    public void save(Guardian guardian) {
        guardianJpaRepository.save(guardian);
    }

    @Override
    public void save(Guardian guardian, String addressId) throws NotFoundException {
        Address address = addressService.find(addressId);
        guardian.setAddress(address);
        save(guardian);
    }

    @Override
    public Guardian find(String id) throws NotFoundException {
        Guardian guardian = guardianJpaRepository.findOne(id);
        if (guardian == null) {
            throw new NotFoundException("Guardian not found");
        }

        return guardian;
    }

}
