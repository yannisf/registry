package fraglab.school.relationship;

import fraglab.GenericDao;
import fraglab.NotFoundException;

import java.util.List;

public interface ChildGuardianRelationshipDao extends GenericDao<ChildGuardianRelationship, Long> {

    List<ChildGuardianRelationship> fetchAllForChild(Long childId);

    ChildGuardianRelationship fetchForChildAndGuardian(Long childId, Long guardianId) throws NotFoundException;

}