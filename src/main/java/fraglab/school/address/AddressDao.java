package fraglab.school.address;

import fraglab.GenericDao;

public interface AddressDao extends GenericDao<Address, String> {

    Long countAddresses(String addressId);
}
