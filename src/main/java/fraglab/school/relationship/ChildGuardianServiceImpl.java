package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.child.ChildDao;
import fraglab.school.guardian.GuardianDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChildGuardianServiceImpl implements ChildGuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(ChildGuardianServiceImpl.class);

    @Autowired
    ChildDao childDao;

    @Autowired
    GuardianDao guardianDao;

    @Autowired
    ChildGuardianRelationshipDao childGuardianRelationshipDao;

    @Override
    public ChildGuardianRelationship fetch(String id) throws NotFoundException {
        return childGuardianRelationshipDao.fetch(id);
    }

    @Override
    public ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException {
        return childGuardianRelationshipDao.fetchForChildAndGuardian(childId, guardianId);
    }

    @Override
    public List<ChildGuardianRelationship> fetchRelationships(String childId) {
        List<ChildGuardianRelationship> relationships = childGuardianRelationshipDao.fetchAllForChild(childId);
        LOG.debug("Fetched {} relationships for child {}", relationships.size(), childId);

        for (ChildGuardianRelationship relationship : relationships) {
            relationship.setGuardian(guardianDao.fetch(relationship.getGuardianId()));
        }

        return relationships;
    }

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationshipDao.delete(childGuardianRelationship);
    }

    @Override
    @Transactional
    public void updateGuardianAndRelationship(ChildGuardianRelationship relationship) {
        guardianDao.update(relationship.getGuardian());
        childGuardianRelationshipDao.update(relationship);
    }

}
