package fraglab.school.address;

import fraglab.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDaoImpl extends GenericDaoImpl<Address, String> implements AddressDao {
}
