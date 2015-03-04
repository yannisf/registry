package fraglab.registry.relationship;

import fraglab.data.GenericDao;
import fraglab.registry.address.Address;
import fraglab.registry.child.Child;
import fraglab.registry.guardian.Guardian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {
    
    @Autowired
    private GenericDao dao;
    
    @Override
    public void createOrUpdate(Relationship relationship) {
        dao.createOrUpdate(relationship);
    }

    @Override
    public void createOrUpdate(Relationship relationship, String childId, String guardianId) {
        Child child = dao.fetch(Child.class, childId);
        Guardian guardian = dao.fetch(Guardian.class, guardianId);
        relationship.setChild(child);
        relationship.setGuardian(guardian);
        createOrUpdate(relationship);
    }

    @Override
    public Relationship fetch(String id) {
        return dao.fetch(Relationship.class, id);
    }

    @Override
    public void delete(String id) {
        Relationship relationship = fetch(id);
        dao.delete(relationship);
    }

    @Override
    public List<Relationship> fetchAllForChild(String childId) {
        Child child = dao.fetch(Child.class, childId);
        child.getRelationships().size();
        return child.getRelationships();
    }

    @Override
    public Relationship fetchForChildAndGuardian(String childId, String guardianId) {
        String query = "select r from Relationship r where r.child.id = :childId and r.guardian.id = :guardianId";
        Map<String, Object> params = new HashMap<>();
        params.put("childId", childId);
        params.put("guardianId", guardianId);
        return dao.findSingleByQuery(Relationship.class, query, params);

    }

}
