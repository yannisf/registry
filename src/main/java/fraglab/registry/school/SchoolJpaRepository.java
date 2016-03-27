package fraglab.registry.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolJpaRepository extends JpaRepository<School, String> {

    List<School> findAllByOrderByNameAsc();
}
