package fraglab.registry.child;

import fraglab.registry.formobject.ChildWithAddress;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ChildService {

    void delete(String id) throws NotFoundException;

    void update(Child child);

    void update(ChildWithAddress childWithAddress);

    Child fetch(String id) throws NotFoundException;

    List<Child> fetchAll();

    List<Child> fetchChildGroup(String id);
}
