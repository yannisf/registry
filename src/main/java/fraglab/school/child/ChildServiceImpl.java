package fraglab.school.child;

import fraglab.NotFoundException;
import fraglab.school.affinity.AffinityDto;
import fraglab.school.affinity.AffinityMetadata;
import fraglab.school.affinity.ChildGrownUpAffinity;
import fraglab.school.grownup.GrownupDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

    private static final Logger LOG = LoggerFactory.getLogger(ChildServiceImpl.class);

    @Autowired
    private ChildDao childDao;

    @Autowired
    private GrownupDao grownupDao;

    @Override
    public void create(Child child) {
        childDao.create(child);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Child child = fetch(id);
        childDao.delete(child);
    }

    @Override
    public void update(Long id, Child child) throws NotFoundException {
        fetch(id);
        child.setId(id);
        childDao.update(child);
    }

    @Override
    public Child fetch(Long id) throws NotFoundException {
        Child child = childDao.fetch(id);
        if (child == null) {
            throw new NotFoundException("Child not found");
        }

        return child;
    }

    @Override
    public List<Child> fetchAll() {
        return childDao.fetchAll();
    }

    @Override
    public ChildGrownUpAffinity fetchAffinity(Long childId, Long grownupId) {
        return childDao.fetch(childId, grownupId);
    }

    @Override
    public void deleteAffinity(Long childId, Long grownupId) {
        childDao.delete(childId, grownupId);
    }

    @Override
    public void createAffinity(Long childId, Long grownupId, AffinityMetadata affinityMetadata) {
        ChildGrownUpAffinity childGrownUpAffinity = new ChildGrownUpAffinity();
        childGrownUpAffinity.setChildId(childId);
        childGrownUpAffinity.setGrownupId(grownupId);
        childGrownUpAffinity.setAffinityMetadata(affinityMetadata);
        childDao.create(childGrownUpAffinity);
    }

    @Override
    public List<AffinityDto> fetchAffinities(Long childId) {
        return childDao.fetchAffinities(childId);
    }
}
