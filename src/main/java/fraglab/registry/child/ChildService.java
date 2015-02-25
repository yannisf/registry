package fraglab.registry.child;

import fraglab.registry.foundation.Group;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ChildService {

    void createOrUpdate(Child child);

    Child fetch(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    List<Child> fetchChildrenForGroup(String id);

    Group fetchGroup(String id);

}
