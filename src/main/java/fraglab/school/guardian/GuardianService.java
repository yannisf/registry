package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.relationship.ChildGuardianRelationship;

import java.util.List;

public interface GuardianService {

    void create(Guardian guardian);

    void delete(Long id) throws NotFoundException;

    void update(Long id, Guardian guardian) throws NotFoundException;

    Guardian fetch(Long id) throws NotFoundException;

    List<Guardian> fetchAll();

    void establishRelationship(Guardian guardian, ChildGuardianRelationship relationship);
}
