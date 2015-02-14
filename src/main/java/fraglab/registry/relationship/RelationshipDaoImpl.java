package fraglab.registry.relationship;

import fraglab.data.GenericDaoImpl;
import fraglab.web.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RelationshipDaoImpl extends GenericDaoImpl<Relationship, String> implements RelationshipDao {

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Relationship> fetchAllForChild(String childId) {
        Query query = entityManager.createQuery("select r from Relationship r where r.childId=:childId");
        query.setParameter("childId", childId);
        return (List<Relationship>) query.getResultList();
    }

    @Override
    public Relationship fetchForChildAndGuardian(String childId, String guardianId) throws NotFoundException {
        Query query = entityManager.createQuery("select r from Relationship r where r.childId=:childId and r.guardianId=:guardianId");
        query.setParameter("childId", childId);
        query.setParameter("guardianId", guardianId);
        Relationship result = (Relationship) query.getSingleResult();
        if (result == null) {
            throw new NotFoundException();
        } else {
            return result;
        }
    }

}
