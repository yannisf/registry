package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.child.ChildDao;
import fraglab.school.guardian.Guardian;
import fraglab.school.guardian.GuardianDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChildGuardianServiceImpl implements ChildGuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(ChildGuardianServiceImpl.class);

    @Autowired
    ChildGuardianRelationshipDao childGuardianRelationshipDao;

    @Autowired
    GuardianDao guardianDao;

    @Autowired
    ChildDao childDao;

    @Override
    public void create(Long childId, Long guardianId, RelationshipMetadata relationshipMetadata) {
        ChildGuardianRelationship childGuardianRelationship = new ChildGuardianRelationship();
        childGuardianRelationship.setChildId(childId);
        childGuardianRelationship.setGuardianId(guardianId);
        childGuardianRelationship.setRelationshipMetadata(relationshipMetadata);
        childGuardianRelationshipDao.create(childGuardianRelationship);
    }

    @Override
    public ChildGuardianRelationship fetch(Long id) throws NotFoundException {
        return childGuardianRelationshipDao.fetch(id);
    }

    @Override
    public ChildGuardianRelationship fetch(Long childId, Long guardianId) throws NotFoundException {
        return childGuardianRelationshipDao.fetchForChildAndGuardian(childId, guardianId);
    }

    @Override
    public void update(Long id, RelationshipMetadata relationshipMetadata) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationship.setRelationshipMetadata(relationshipMetadata);
        childGuardianRelationshipDao.update(childGuardianRelationship);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationshipDao.delete(childGuardianRelationship);
    }

    @Override
    public List<GuardianRelationshipDto> fetchRelationshipDtos(Long childId) {
        List<ChildGuardianRelationship> childGuardianRelationships = childGuardianRelationshipDao.fetchAllForChild(childId);
        List<GuardianRelationshipDto> guardianRelationshipDtos = new ArrayList<>();
        for (ChildGuardianRelationship childGuardianRelationship : childGuardianRelationships) {
            GuardianRelationshipDto dto = new GuardianRelationshipDto();
            dto.setRelationship(childGuardianRelationship);
            dto.setGuardian(guardianDao.fetch(childGuardianRelationship.getGuardianId()));
            guardianRelationshipDtos.add(dto);
        }
        return guardianRelationshipDtos;
    }

    @Override
    @Transactional
    public void createGuardianAndRelationship(Guardian guardian, ChildGuardianRelationship relationship) {
        guardianDao.create(guardian);
        relationship.setGuardianId(guardian.getId());
        childGuardianRelationshipDao.create(relationship);
    }

    @Override
    @Transactional
    public void updateGuardianAndRelationship(Long childId, Long guardianId, GuardianRelationshipDto guardianRelationship)
            throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(childId, guardianId);
        childGuardianRelationship.setRelationshipMetadata(guardianRelationship.getRelationship().getRelationshipMetadata());
        guardianDao.update(guardianRelationship.getGuardian());
    }

}
