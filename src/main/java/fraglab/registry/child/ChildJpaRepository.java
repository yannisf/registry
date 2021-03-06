package fraglab.registry.child;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildJpaRepository extends JpaRepository<Child, String> {

    List<Child> findByGroupId(@Param("groupId") String groupId);

    @Query("select distinct c from Child c left join fetch c.relationships where c.id=:id")
    Child queryForRelationships(@Param("id") String id);

    @Query("select c.id from Child c where c.group.id=:groupId order by c.lastName")
    List<String> queryByGroupForIds(@Param("groupId") String groupId);

}
