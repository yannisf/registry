package fraglab.school.child;

import fraglab.NotFoundException;

import java.util.List;

public interface ChildService {

    void delete(String id) throws NotFoundException;

    void update(Child child) throws NotFoundException;

    Child fetch(String id) throws NotFoundException;

    List<Child> fetchAll();

}
