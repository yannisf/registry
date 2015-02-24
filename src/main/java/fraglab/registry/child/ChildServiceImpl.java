package fraglab.registry.child;

import fraglab.registry.address.AddressService;
import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    private static final Logger LOG = LoggerFactory.getLogger(ChildServiceImpl.class);

    @Autowired
    private ChildDao childDao;

    @Autowired
    private AddressService addressService;

    @Override
    public void delete(String id) throws NotFoundException {
        Child child = fetch(id);
        childDao.delete(child);
        addressService.delete(child.getAddress().getId());
        childDao.updateChildGroupMembers(child.getChildGroup().getId());
    }

    @Override
    public void update(Child child) {
        childDao.update(child);
        childDao.updateChildGroupMembers(child.getChildGroup().getId());
    }

    @Override
    public Child fetch(String id) throws NotFoundException {
        Child child = childDao.fetch(id);
        if (child == null) {
            throw new NotFoundException("Child not found");
        }

        return child;
    }

    @Override
    public List<Child> fetchChildGroup(String id) {
        return childDao.fetchClassroom(id);
    }

}
