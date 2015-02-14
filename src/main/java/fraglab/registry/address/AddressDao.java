package fraglab.registry.address;

import fraglab.data.GenericDao;

public interface AddressDao extends GenericDao<Address, String> {

    Long countAddresses(String addressId);
}
