package fraglab.registry.relationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipJpaRepository extends JpaRepository<Relationship, String> {

    Relationship findByChildIdAndGuardianId(String childId, String guardianId);

}
