package fraglab.registry.child;

import fraglab.registry.overview.Group;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;

public interface ChildService {

    void createOrUpdate(Child child) throws NotIdentifiedException;

    void createOrUpdate(Child child, String addressId, String groupId) throws NotIdentifiedException, NotFoundException;
    
    Child fetch(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    Group fetchGroup(String id);

    Child fetchWithRelationships(String id);
}
