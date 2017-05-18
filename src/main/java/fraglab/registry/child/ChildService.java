package fraglab.registry.child;

import java.util.Map;
import java.util.Optional;

public interface ChildService {

    Optional<Child> find(String id);

    Child save(Child child);

    Child save(Child child, String addressId, String groupId);

    void delete(String id);

    Optional<Child> findWithRelationships(String id);

    Map<String, Map<String, String>> findEmailsForGroup(String groupId);

    String emailsForGroup(String groupId);

    Optional<ChildPhoto> findChildPhoto(String id);

    void deleteChildPhoto(String id);

    void saveChildPhoto(String childId, byte[] bytes);
}
