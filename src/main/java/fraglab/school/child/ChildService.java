package fraglab.school.child;

import fraglab.NotFoundException;

import java.util.List;

public interface ChildService {

    void create(Child child);

    void delete(Long id) throws NotFoundException;

    void update(Long id, Child child) throws NotFoundException;

    Child fetch(Long id) throws NotFoundException;

    List<Child> fetchAll();

}
