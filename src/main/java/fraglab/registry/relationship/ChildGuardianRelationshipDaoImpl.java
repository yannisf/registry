package fraglab.registry.relationship;

import fraglab.GenericDaoImpl;
import fraglab.web.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChildGuardianRelationshipDaoImpl extends GenericDaoImpl<ChildGuardianRelationship, String> implements ChildGuardianRelationshipDao {

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<ChildGuardianRelationship> fetchAllForChild(String childId) {
        Query query = entityManager.createQuery("select r from ChildGuardianRelationship r where r.childId=:childId");
        query.setParameter("childId", childId);
        return (List<ChildGuardianRelationship>) query.getResultList();
    }

    @Override
    public ChildGuardianRelationship fetchForChildAndGuardian(String childId, String guardianId) throws NotFoundException {
        Query query = entityManager.createQuery("select r from ChildGuardianRelationship r where r.childId=:childId and r.guardianId=:guardianId");
        query.setParameter("childId", childId);
        query.setParameter("guardianId", guardianId);
        ChildGuardianRelationship result = (ChildGuardianRelationship) query.getSingleResult();
        if (result == null) {
            throw new NotFoundException();
        } else {
            return result;
        }
    }

}
