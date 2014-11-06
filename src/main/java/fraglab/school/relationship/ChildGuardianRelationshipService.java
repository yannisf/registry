package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.formobject.RelationshipWithGuardianAndAddress;

import java.util.List;

public interface ChildGuardianRelationshipService {

    ChildGuardianRelationship fetch(String id) throws NotFoundException;

    ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException;

    List<ChildGuardianRelationship> fetchRelationships(String childId) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    void updateRelationshipWithGuardianAndAddress(RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress);
}
