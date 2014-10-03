package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GuardianServiceImpl implements GuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianServiceImpl.class);

    @Autowired
    GuardianDao guardianDao;

    @Autowired
    RelationshipDao relationshipDao;

    @Override
    public void create(Guardian guardian) {
        guardianDao.create(guardian);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Guardian guardian = fetch(id);
        guardianDao.delete(guardian);
    }

    @Override
    public void update(Long id, Guardian guardian) throws NotFoundException {
        fetch(id);
        guardian.setId(id);
        guardianDao.update(guardian);
    }

    @Override
    public Guardian fetch(Long id) throws NotFoundException {
        Guardian guardian = guardianDao.fetch(id);
        if (guardian == null) {
            throw new NotFoundException("Guardian not found");
        }

        return guardian;
    }

    @Override
    public List<Guardian> fetchAll() {
        return guardianDao.fetchAll();
    }

    @Override
    @Transactional
    public void establishRelationship(Guardian guardian, ChildGuardianRelationship relationship) {
        if (guardian.getId() == null) {
            guardianDao.create(guardian);
        }
        relationship.setGuardianId(guardian.getId());
        relationshipDao.create(relationship);
    }

}
