package fraglab.registry.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentJpaRepository extends JpaRepository<Department, String> {

    @Query("select c from Department c where c.school.id=:schoolId order by c.name")
    List<Department> queryBySchoolId(@Param("schoolId") String schoolId);

}
