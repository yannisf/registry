package fraglab.registry.child;

import fraglab.registry.group.Group;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;

public interface ChildService {

    void save(Child child) throws NotIdentifiedException;

    void save(Child child, String addressId, String groupId) throws NotIdentifiedException, NotFoundException;
    
    Child find(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    Child findWithRelationships(String id);
}
