package fraglab.registry.child;

import fraglab.NotFoundException;
import fraglab.registry.formobject.ChildWithAddress;

import java.util.List;

public interface ChildService {

    void delete(String id) throws NotFoundException;

    void update(Child child);

    void update(ChildWithAddress childWithAddress);

    Child fetch(String id) throws NotFoundException;

    List<Child> fetchAll();

    List<Child> fetchClass(String id);
}