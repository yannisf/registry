package fraglab.registry.relationship;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildJpaRepository;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianJpaRepository;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {
    
    @Autowired
    private ChildJpaRepository childJpaRepository;

    @Autowired
    private GuardianJpaRepository guardianJpaRepository;

    @Autowired
    private RelationshipJpaRepository relationshipJpaRepository;

    @Override
    public void save(Relationship relationship) {
        relationshipJpaRepository.save(relationship);
    }

    @Override
    public void save(Relationship relationship, String childId, String guardianId) {
        Child child = childJpaRepository.findOne(childId);
        Guardian guardian = guardianJpaRepository.findOne(guardianId);
        relationship.setChild(child);
        relationship.setGuardian(guardian);
        save(relationship);
    }

    @Override
    public Relationship find(String id) throws NotFoundException {
        Relationship relationship = relationshipJpaRepository.findOne(id);
        if (relationship == null) {
            throw new NotFoundException("Relationship not found");
        }
        return relationship;
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Relationship relationship = find(id);
        relationshipJpaRepository.delete(relationship);
    }

    @Override
    public List<Relationship> findAllForChild(String childId) {
        Child child = childJpaRepository.findOne(childId);
        child.getRelationships().size();
        return child.getRelationships();
    }

    @Override
    public Relationship findForChildAndGuardian(String childId, String guardianId) {
        return relationshipJpaRepository.queryByChildAndGuardian(childId, guardianId);

    }

}
