package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.guardian.Guardian;

import java.util.Set;

public interface ChildGuardianService {

    void create(Long childId, Long guardianId, RelationshipMetadata relationshipMetadata);

    ChildGuardianRelationship fetch(Long id) throws NotFoundException;

    ChildGuardianRelationship fetch(Long childId, Long guardianId) throws NotFoundException;

    void update(Long relationshipId, RelationshipMetadata relationshipMetadata) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    Set<GuardianRelationshipDto> fetchRelationshipDtos(Long childId);

    void createGuardianAndRelationship(Guardian guardian, ChildGuardianRelationship relationship);

    void updateGuardianAndRelationship(Long childId, Long guardianId, GuardianRelationshipDto guardianRelationship) throws NotFoundException;

}
