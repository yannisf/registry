package fraglab.registry.guardian;

import fraglab.web.NotFoundException;

public interface GuardianService {

    void save(Guardian guardian);

    void save(Guardian guardian, String addressId) throws NotFoundException;

    Guardian find(String id) throws NotFoundException;

    void delete(String id) throws NotFoundException;

}
