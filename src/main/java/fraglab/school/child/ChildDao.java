package fraglab.school.child;

import fraglab.GenericDao;
import fraglab.school.affinity.AffinityDto;
import fraglab.school.affinity.ChildGrownUpAffinity;

import java.util.List;

public interface ChildDao extends GenericDao<Child, Long> {

    List<Child> fetchAll();

    ChildGrownUpAffinity fetch(Long childId, Long grownupId);

    void delete(Long childId, Long grownupId);

    void create(ChildGrownUpAffinity childGrownUpAffinity);

    List<AffinityDto> fetchAffinities(Long childId);
}
