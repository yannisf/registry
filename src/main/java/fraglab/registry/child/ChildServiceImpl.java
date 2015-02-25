package fraglab.registry.child;

import fraglab.data.GenericDao;
import fraglab.registry.address.AddressService;
import fraglab.registry.foundation.Group;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    @Autowired
    private GenericDao<Child> childDao;

    @Autowired
    private GenericDao<Group> groupDao;

    @Autowired
    private AddressService addressService;

    @Override
    public void delete(String id) throws NotFoundException {
        Child child = fetch(id);
        childDao.delete(child);
        updateGroupMembersNum(child.getGroup());
    }

    @Override
    public void createOrUpdate(Child child) {
        childDao.createOrUpdate(child);
        updateGroupMembersNum(child.getGroup());
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
    public List<Child> fetchChildrenForGroup(String id) {
        String query = "select c from Child c where c.group.id=:groupId";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", id);
        return  childDao.findByQuery(query, params);
    }

    @Override
    public Group fetchGroup(String id) {
        return groupDao.fetch(id);
    }

    private void updateGroupMembersNum(Group group) {
        String query = "createOrUpdate Group g set g.members=(select count(c) from Child c where c.group=:group) where g=:group";
        Map<String, Object> params = new HashMap<>();
        params.put("group", group);
        childDao.executeUpdate(query, params);
    }

}
