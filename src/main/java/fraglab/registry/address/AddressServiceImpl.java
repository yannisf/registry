package fraglab.registry.address;

import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Override
    public void save(Address address) {
        addressJpaRepository.save(address);
    }

    @Override
    public Address find(String id) throws NotFoundException {
        Address address = addressJpaRepository.findOne(id);
        if (address == null) {
            throw new NotFoundException("Address not found");
        }
        return address;
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Address address = find(id);
        addressJpaRepository.delete(address);
    }

    @Override
    public Address findForPerson(String personId) {
        return addressJpaRepository.queryByPersonId(personId);
    }

}
