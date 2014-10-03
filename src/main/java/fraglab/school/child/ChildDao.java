package fraglab.school.child;

import fraglab.GenericDao;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDto;

import java.util.List;

public interface ChildDao extends GenericDao<Child, Long> {

    List<Child> fetchAll();

    ChildGuardianRelationship fetch(Long childId, Long guardianId);

    void delete(Long childId, Long guardianId);

    void create(ChildGuardianRelationship childGuardianRelationship);

    List<RelationshipDto> fetchRelationship(Long childId);
}
