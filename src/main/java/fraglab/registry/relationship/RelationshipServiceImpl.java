package fraglab.registry.relationship;

import fraglab.data.GenericDao;
import fraglab.registry.child.Child;
import fraglab.registry.guardian.Guardian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<Relationship> fetchForChild(String childId) {
        Child child = dao.fetch(Child.class, childId);
        child.getRelationships().size();
        return child.getRelationships();
    }
    
}
