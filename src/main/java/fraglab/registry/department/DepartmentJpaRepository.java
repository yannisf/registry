package fraglab.registry.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentJpaRepository extends JpaRepository<Department, String> {

    List<Department> findBySchoolIdOrderByName(String schoolId);

}
