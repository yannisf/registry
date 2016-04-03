package fraglab.registry.child;

import fraglab.web.NotIdentifiedException;

import java.util.Map;
import java.util.Optional;

public interface ChildService {

    Optional<Child> find(String id);

    Child save(Child child) throws NotIdentifiedException;

    Child save(Child child, String addressId, String groupId) throws NotIdentifiedException;

    void delete(String id);

    Optional<Child> findWithRelationships(String id);

    Map<String, Map<String, String>> findEmailsForGroup(String groupId);

    String emailsForGroup(String groupId);
}
