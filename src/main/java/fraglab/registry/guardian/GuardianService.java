package fraglab.registry.guardian;

import fraglab.web.NotFoundException;

import java.util.Optional;

public interface GuardianService {

    Optional<Guardian> find(String id);

    Guardian save(Guardian guardian);

    Guardian save(Guardian guardian, String addressId);

    void delete(String id);

}
