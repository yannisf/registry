package fraglab.school.child;

import fraglab.NotFoundException;
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


}
