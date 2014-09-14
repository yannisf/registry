package fraglab.school.child;

import fraglab.GenericDao;

import java.util.List;

public interface ChildDao extends GenericDao<Child, Long> {

    List<Child> fetchAll();

}
