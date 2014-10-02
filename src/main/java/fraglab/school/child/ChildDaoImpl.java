package fraglab.school.child;

import fraglab.GenericDaoImpl;
import fraglab.school.guardian.Guardian;
import fraglab.school.guardian.GuardianDao;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl extends GenericDaoImpl<Child, Long> implements ChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChildDaoImpl.class);

    @Autowired
    private GuardianDao guardianDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Child> fetchAll() {
        LOG.debug("Fetching all Child(ren)");
        Query query = entityManager.createQuery("select c from Child c");
        return query.getResultList();
    }

    @Override
    public ChildGuardianRelationship fetch(Long childId, Long guardianId) {
        LOG.debug("Fetching relationship for Child[{}]:Guardian[{}]", childId, guardianId);
        Query query = entityManager.createQuery("select a from ChildGuardianRelationship a where a.childId=:childId and a.guardianId=:guardianId");
        query.setParameter("childId", childId);
        query.setParameter("guardianId", guardianId);
        return (ChildGuardianRelationship) query.getSingleResult();
    }

    @Override
    public void delete(Long childId, Long guardianId) {
        ChildGuardianRelationship relationship = fetch(childId, guardianId);
        entityManager.remove(relationship);
    }

    @Override
    public void create(ChildGuardianRelationship childGuardianRelationship) {
        entityManager.persist(childGuardianRelationship);
    }

    @Override
    public List<RelationshipDto> fetchAffinities(Long childId) {
        Query query = entityManager.createQuery("select a from ChildGuardianRelationship a where a.childId=:childId");
        query.setParameter("childId", childId);
        List<ChildGuardianRelationship> affinities = query.getResultList();
        List<RelationshipDto> relationshipDtos = getRelationshipDtos(affinities);
        return relationshipDtos;
    }

    private List<RelationshipDto> getRelationshipDtos(List<ChildGuardianRelationship> affinities) {
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        for (ChildGuardianRelationship childGuardianRelationship : affinities) {
            Guardian guardian = guardianDao.fetch(childGuardianRelationship.getGuardianId());
            RelationshipDto relationshipDto = new RelationshipDto(guardian.getId(), guardian.getFirstName(), guardian.getLastName(),
                    childGuardianRelationship.getRelationshipMetadata().getType());
            relationshipDtos.add(relationshipDto);
        }
        return relationshipDtos;
    }
}
