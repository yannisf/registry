package fraglab.school.guardian;

import fraglab.NotFoundException;

public interface GuardianService {

    void delete(String id) throws NotFoundException;

    void update(Guardian guardian);

    Guardian fetch(String id) throws NotFoundException;

}
