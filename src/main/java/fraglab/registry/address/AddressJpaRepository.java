package fraglab.registry.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressJpaRepository extends JpaRepository<Address, String> {

    @Query("select p.address from Person p where p.id=:personId")
    Address queryByPersonId(@Param("personId") String personId);

}
