package fraglab.registry.guardian;

import fraglab.registry.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GuardianServiceImpl implements GuardianService {

    @Autowired
    private GuardianJpaRepository guardianJpaRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public Optional<Guardian> find(String id) {
        return Optional.ofNullable(guardianJpaRepository.findOne(id));
    }

    @Override
    public Guardian save(Guardian guardian) {
        return guardianJpaRepository.save(guardian);
    }

    @Override
    public Guardian save(Guardian guardian, String addressId) {
        addressService.find(addressId).ifPresent(guardian::setAddress);
        return save(guardian);
    }

    @Override
    public void delete(String guardianId) {
        guardianJpaRepository.delete(guardianId);
    }

}
