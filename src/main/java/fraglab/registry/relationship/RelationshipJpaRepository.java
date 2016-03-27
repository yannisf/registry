package fraglab.registry.relationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipJpaRepository extends JpaRepository<Relationship, String> {

    @Query("select r from Relationship r where r.child.id=:childId and r.guardian.id=:guardianId")
    Relationship queryByChildAndGuardian(@Param("childId") String childId, @Param("guardianId") String guardianId);

}
