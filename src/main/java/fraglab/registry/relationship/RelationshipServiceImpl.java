package fraglab.registry.relationship;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildJpaRepository;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public Relationship save(Relationship relationship) {
        return relationshipJpaRepository.save(relationship);
    }

    @Override
    public Relationship save(Relationship relationship, String childId, String guardianId) {
        Child child = childJpaRepository.findOne(childId);
        Guardian guardian = guardianJpaRepository.findOne(guardianId);
        relationship.setChild(child);
        relationship.setGuardian(guardian);
        return save(relationship);
    }

    @Override
    public Optional<Relationship> find(String id) {
        return Optional.ofNullable(relationshipJpaRepository.findOne(id));
    }

    @Override
    public void delete(String relationshipId) {
        relationshipJpaRepository.delete(relationshipId);
    }

    @Override
    public List<Relationship> findAllForChild(String childId) {
        //TODO: Do this in the relationship repo
        Child child = childJpaRepository.findOne(childId);
        Collections.sort(child.getRelationships());
        return child.getRelationships();
    }

    @Override
    public Optional<Relationship> findForChildAndGuardian(String childId, String guardianId) {
        return Optional.ofNullable(relationshipJpaRepository.findByChildIdAndGuardianId(childId, guardianId));

    }

}
