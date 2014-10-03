package fraglab.school.child;

import fraglab.NotFoundException;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDto;
import fraglab.school.relationship.RelationshipMetadata;

import java.util.List;

public interface ChildService {

    void create(Child child);

    void delete(Long id) throws NotFoundException;

    void update(Long id, Child child) throws NotFoundException;

    Child fetch(Long id) throws NotFoundException;

    List<Child> fetchAll();

    ChildGuardianRelationship fetchRelationship(Long childId, Long guardianId);

    void deleteRelationship(Long childId, Long guardianId);

    void createRelationship(Long childId, Long guardianId, RelationshipMetadata relationshipMetadata);

    List<RelationshipDto> fetchRelationships(Long childId);
}
