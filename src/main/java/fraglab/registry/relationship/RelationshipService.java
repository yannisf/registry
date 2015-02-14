package fraglab.registry.relationship;

import fraglab.registry.formobject.RelationshipWithGuardianAndAddress;
import fraglab.web.NotFoundException;

import java.util.List;

public interface RelationshipService {

    Relationship fetch(String id) throws NotFoundException;

    Relationship fetch(String childId, String guardianId) throws NotFoundException;

    List<Relationship> fetchRelationships(String childId) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    void updateRelationshipWithGuardianAndAddress(RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress);
}
