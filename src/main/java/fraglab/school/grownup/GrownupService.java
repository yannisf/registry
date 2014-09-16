package fraglab.school.grownup;

import fraglab.NotFoundException;

import java.util.List;

public interface GrownupService {

    void create(Grownup grownup);

    void delete(Long id) throws NotFoundException;

    void update(Long id, Grownup grownup) throws NotFoundException;

    Grownup fetch(Long id) throws NotFoundException;

    List<Grownup> fetchAll();

}
