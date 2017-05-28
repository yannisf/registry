package fraglab.registry.child;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildPhotoJpaRepository extends JpaRepository<ChildPhoto, String> {

}
