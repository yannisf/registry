package fraglab.school.grownup;

import fraglab.GenericDao;

import java.util.List;

public interface GrownupDao extends GenericDao<Grownup, Long> {

    List<Grownup> fetchAll();

}
