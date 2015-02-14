package fraglab.registry.address;

import fraglab.data.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDaoImpl extends GenericDaoImpl<Address, String> implements AddressDao {
    @Override
    public Long countAddresses(String addressId) {
        Query query = entityManager.createQuery("select count(p.addressId) from Person p where p.addressId = :addressId");
        return (Long) query.setParameter("addressId", addressId).getSingleResult();
    }
}
