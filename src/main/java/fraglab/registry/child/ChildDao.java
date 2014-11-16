package fraglab.registry.child;

import fraglab.GenericDao;

import java.util.List;

public interface ChildDao extends GenericDao<Child, String> {

    List<Child> fetchAll();

    List<Child> fetchClass(String id);
}
