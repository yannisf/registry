package fraglab.school.child;

import fraglab.school.model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    ChildDao childDao;

    @Override
    public void create(Child child) {
        childDao.create(child);
    }

    @Override
    public void delete(Long id) {
        childDao.delete(id);
    }

    @Override
    public void update(Child child) {
        childDao.update(child);
    }

    @Override
    public Child fetch(Long id) {
        return childDao.fetch(id);
    }

    @Override
    public List<Child> fetchAll() {
        return childDao.fetchAll();
    }

}
