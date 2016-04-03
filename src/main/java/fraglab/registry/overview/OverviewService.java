package fraglab.registry.overview;

import fraglab.registry.child.Child;
import fraglab.registry.department.Department;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupDataTransfer;
import fraglab.registry.group.GroupStatistics;
import fraglab.registry.school.School;
import fraglab.web.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OverviewService {

    GroupDataTransfer findSchoolData(String childGroupId);

    List<Child> findChildrenForGroup(String groupId);

    List<String> findChildrenIdsForGroup(String groupId);

    GroupStatistics findChildGroupStatistics(String childGroupId);

    School saveSchool(School school);

    Department saveDepartment(Department department);

    Group saveGroup(Group group);

    List<School> findSchools();

    Optional<School> findSchool(String id);

    Optional<Department> findDepartment(String id);

    List<Department> findDepartmentsForSchool(String schoolId);

    void deleteSchool(String id);

    Department saveDepartmentForSchool(String schoolId, Department department);

    List<Group> findGroupsForDepartment(String departmentId);

    void deleteDepartment(String departmentId);

    Group saveGroupForDepartment(Group group, String departmentId);

    void deleteGroup(String groupId);

    Map<String,Object> findGroupInfo(String id);
}
