package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.child.ChildDao;
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
    public ChildGuardianRelationship fetch(String id) throws NotFoundException {
        return childGuardianRelationshipDao.fetch(id);
    }

    @Override
    public ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException {
        return childGuardianRelationshipDao.fetchForChildAndGuardian(childId, guardianId);
    }

    @Override
    public List<RelationshipDto> fetchRelationships(String childId) {
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        List<ChildGuardianRelationship> relationships = childGuardianRelationshipDao.fetchAllForChild(childId);
        LOG.debug("Fetched {} relationships for child {}", relationships.size(), childId);

        for (ChildGuardianRelationship relationship : relationships) {
            RelationshipDto relationshipDto = new RelationshipDto();
            relationshipDto.setRelationshipId(relationship.getId());
            relationshipDto.setChildId(childId);
            relationshipDto.setGuardian(guardianDao.fetch(relationship.getGuardianId()));
            relationshipDto.setRelationshipMetadata(relationship.getRelationshipMetadata());
            relationshipDtos.add(relationshipDto);
        }

        return relationshipDtos;
    }

    @Override
    public void update(String id, RelationshipMetadata relationshipMetadata) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationship.setRelationshipMetadata(relationshipMetadata);
        childGuardianRelationshipDao.update(childGuardianRelationship);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationshipDao.delete(childGuardianRelationship);
    }

    @Override
    @Transactional
    public void updateGuardianAndRelationship(String childId, String guardianId, RelationshipDto relationshipDto) {
        guardianDao.update(relationshipDto.getGuardian());

        ChildGuardianRelationship childGuardianRelationship;
        try {
            childGuardianRelationship = childGuardianRelationshipDao.fetchForChildAndGuardian(childId, guardianId);
        } catch (NotFoundException e) {
            childGuardianRelationship = new ChildGuardianRelationship();
        }

        childGuardianRelationship.setChildId(childId);
        childGuardianRelationship.setGuardianId(guardianId);
        childGuardianRelationship.setRelationshipMetadata(relationshipDto.getRelationshipMetadata());
        childGuardianRelationshipDao.update(childGuardianRelationship);
    }

}
