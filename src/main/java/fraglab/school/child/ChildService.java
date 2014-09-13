package fraglab.school.child;

import fraglab.school.model.Child;

import java.util.List;

public interface ChildService {

    void create(Child child);

    //TODO: Discuss if this should take as parameter Child instead of Long
    void delete(Long id);

    void update(Child child);

    Child fetch(Long id);

    List<Child> fetchAll();

}
