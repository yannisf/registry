package fraglab.registry.relationship;

import java.util.List;
import java.util.Optional;

public interface RelationshipService {

    Optional<Relationship> find(String id);

    Relationship save(Relationship relationship);

    Relationship save(Relationship relationship, String childId, String guardianId);

    void delete(String id);

    List<Relationship> findAllForChild(String childId);

    Optional<Relationship> findForChildAndGuardian(String childId, String guardianId);

}
