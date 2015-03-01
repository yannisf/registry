package fraglab.registry.guardian;

import fraglab.web.NotFoundException;

public interface GuardianService {

    void createOrUpdate(Guardian guardian);

    void createOrUpdate(Guardian guardian, String addressId);

    Guardian fetch(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

}
