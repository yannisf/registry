package fraglab.registry.child;

import fraglab.registry.school.Group;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ChildService {

    void delete(String id) throws NotFoundException;

    void update(Child child);

    Child fetch(String id) throws NotFoundException;

    List<Child> fetchChildGroup(String id);

    Group fetchGroup(String id);
}
