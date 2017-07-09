package fraglab.registry.child;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChildService {

    Optional<Child> find(String id);

    Child save(Child child);

    Child save(Child child, String addressId, String groupId);

    void delete(String id);

    Optional<Child> findWithRelationships(String id);

    Map<String, List<String>> findEmailsForGroup(String groupId);

    String emailsForGroup(String groupId) throws IOException, TemplateException;

    Optional<ChildPhoto> findChildPhoto(String id);

    void deleteChildPhoto(String id);

    String saveChildPhoto(String childId, byte[] bytes);
}
