package fraglab.school.relationship;

import fraglab.NotFoundException;

import java.util.List;

public interface ChildGuardianService {

    ChildGuardianRelationship fetch(String id) throws NotFoundException;

    ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException;

    List<ChildGuardianRelationship> fetchRelationships(String childId) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    void updateGuardianAndRelationship(ChildGuardianRelationship relationship);

}
