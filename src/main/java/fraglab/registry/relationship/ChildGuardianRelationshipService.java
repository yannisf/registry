package fraglab.registry.relationship;

import fraglab.registry.formobject.RelationshipWithGuardianAndAddress;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ChildGuardianRelationshipService {

    ChildGuardianRelationship fetch(String id) throws NotFoundException;

    ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException;

    List<ChildGuardianRelationship> fetchRelationships(String childId) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    void updateRelationshipWithGuardianAndAddress(RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress);
}
