package fraglab.registry.child;

import fraglab.data.GenericDao;
import fraglab.registry.school.Group;

import java.util.List;

public interface ChildDao extends GenericDao<Child, String> {

    Group fetchChildGroup(String id);
    
    List<Child> fetchGroup(String id);

    void updateChildGroupMembers(String childGroupId);
    
}
