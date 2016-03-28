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

public interface OverviewService {

    GroupDataTransfer fetchSchoolData(String childGroupId);

    List<Child> findChildrenForGroup(String groupId);

    List<String> findChildrenIdsForGroup(String groupId);

    GroupStatistics findChildGroupStatistics(String childGroupId);

    void createOrUpdateSchool(School school);

    void createOrUpdateDepartment(Department department);

    void createOrUpdateGroup(Group group);

    List<School> fetchSchools();

    School findSchool(String id) throws NotFoundException;

    Department findDepartment(String id) throws NotFoundException;

    List<Department> findDepartmentsForSchool(String schoolId);

    void deleteSchool(String id);

    void saveDepartmentForSchool(String schoolId, Department department) throws NotFoundException;

    List<Group> findGroupsForDepartment(String departmentId) throws NotFoundException;

    void deleteDepartment(String departmentId);

    void saveGroupForDepartment(Group group, String departmentId) throws NotFoundException;

    void deleteGroup(String groupId);

    Map<String,Object> fetchGroupInfo(String id) throws NotFoundException;
}
