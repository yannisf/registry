package fraglab.registry.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Override
    public Optional<Address> find(String id) {
        return Optional.ofNullable(addressJpaRepository.findOne(id));
    }

    @Override
    public Address save(Address address) {
        return addressJpaRepository.save(address);
    }

    @Override
    public void delete(String addressId) {
        addressJpaRepository.delete(addressId);
    }

    @Override
    public Address findForPerson(String personId) {
        return addressJpaRepository.queryByPersonId(personId);
    }

}
