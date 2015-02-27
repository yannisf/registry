package fraglab.registry.child;

import fraglab.registry.foundation.Group;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;

import java.util.List;

public interface ChildService {

    void createOrUpdate(Child child) throws NotIdentifiedException;

    Child fetch(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    Group fetchGroup(String id);

}
