package fraglab.school.child;

import fraglab.NotFoundException;
import fraglab.school.affinity.AffinityDto;
import fraglab.school.affinity.AffinityMetadata;
import fraglab.school.affinity.ChildGrownUpAffinity;

import java.util.List;

public interface ChildService {

    void create(Child child);

    void delete(Long id) throws NotFoundException;

    void update(Long id, Child child) throws NotFoundException;

    Child fetch(Long id) throws NotFoundException;

    List<Child> fetchAll();

    ChildGrownUpAffinity fetchAffinity(Long childId, Long grownupId);

    void deleteAffinity(Long childId, Long grownupId);

    void createAffinity(Long childId, Long grownupId, AffinityMetadata affinityMetadata);

    List<AffinityDto> fetchAffinities(Long childId);
}
