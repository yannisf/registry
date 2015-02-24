package fraglab.registry.address;

import fraglab.data.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDaoImpl extends GenericDaoImpl<Address, String> implements AddressDao {
    @Override
}
