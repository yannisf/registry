package fraglab.school.child;

import fraglab.school.model.Child;

import java.util.List;

public interface ChildDao {

    Child fetch(Long id);

    List<Child> fetchAll();

    void create(Child child);

    void delete(Long id);

    void update(Child child);
}
