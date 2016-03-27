package fraglab.registry.child;

import fraglab.registry.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildJpaRepository extends JpaRepository<Child, String> {

    @Query("select distinct c from Child c left join fetch c.relationships where c.id=:id")
    Child queryForRelationships(@Param("id") String id);

    @Query("select c from Child c where c.group.id=:groupId")
    List<Child> queryForGroup(@Param("groupId") String groupId);

}
