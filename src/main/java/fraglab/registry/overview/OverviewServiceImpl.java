package fraglab.registry.overview;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildJpaRepository;
import fraglab.registry.department.Department;
import fraglab.registry.department.DepartmentJpaRepository;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupDataTransfer;
import fraglab.registry.group.GroupJpaRepository;
import fraglab.registry.group.GroupStatistics;
import fraglab.registry.school.School;
import fraglab.registry.school.SchoolJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private ChildJpaRepository childJpaRepository;

    @Autowired
    private SchoolJpaRepository schoolJpaRepository;

    @Autowired
    private DepartmentJpaRepository departmentJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Override
    public GroupDataTransfer findSchoolData(String groupId) {
        return groupJpaRepository.queryByGroupForMetadata(groupId);
    }

    @Override
    public List<Child> findChildrenForGroup(String groupId) {
        return childJpaRepository.findByGroupId(groupId);
    }

    @Override
    public List<String> findChildrenIdsForGroup(String groupId) {
        return childJpaRepository.queryByGroupForIds(groupId);
    }

    @Override
    public GroupStatistics findChildGroupStatistics(String groupId) {
        Object[] result = (Object[]) groupJpaRepository.queryByGroupForStatistics(groupId);
        return new GroupStatistics(groupId,
                ((BigInteger) result[0]).intValue(),
                ((BigInteger) result[1]).intValue(),
                ((BigInteger) result[2]).intValue(),
                ((BigInteger) result[3]).intValue());
    }

    @Override
    public School saveSchool(School school) {
        return schoolJpaRepository.save(school);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentJpaRepository.save(department);
    }

    @Override
    public Group saveGroup(Group group) {
        return groupJpaRepository.save(group);
    }

    @Override
    public List<School> findSchools() {
        return schoolJpaRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<School> findSchool(String id) {
        return Optional.ofNullable(schoolJpaRepository.findOne(id));
    }

    @Override
    public Optional<Department> findDepartment(String id) {
        return Optional.ofNullable(departmentJpaRepository.findOne(id));
    }

    @Override
    public List<Department> findDepartmentsForSchool(String schoolId) {
        return departmentJpaRepository.findBySchoolIdOrderByName(schoolId);
    }

    @Override
    public void deleteSchool(String schoolId) {
        schoolJpaRepository.delete(schoolId);
    }

    @Override
    public Department saveDepartmentForSchool(String schoolId, Department department) {
        School school = findSchool(schoolId).orElseThrow(
                () -> new IllegalArgumentException("School not found"));
        school.addDepartment(department);
        return departmentJpaRepository.save(department);
    }

    @Override
    public List<Group> findGroupsForDepartment(String departmentId) {
        Department department = findDepartment(departmentId).orElseThrow(
                () -> new IllegalArgumentException("Department not found"));
        department.getGroups().size();
        return department.getGroups();
    }

    @Override
    public void deleteDepartment(String departmentId) {
        departmentJpaRepository.delete(departmentId);
    }

    @Override
    public Group saveGroupForDepartment(Group group, String departmentId) {
        Department department = findDepartment(departmentId).orElseThrow(
                () -> new IllegalArgumentException("Department not found"));
        group.setDepartment(department);
        return groupJpaRepository.save(group);
    }

    @Override
    public void deleteGroup(String groupId) {
        groupJpaRepository.delete(groupId);
    }

    @Override
    public Map<String, Object> findGroupInfo(String groupId) {
        Group group = findGroup(groupId).orElseThrow(
                () -> new IllegalArgumentException("Group not found"));
        Department department = findDepartment(group.getDepartment().getId()).orElseThrow(
                () -> new IllegalArgumentException("Department not found"));
        School school = findSchool(department.getSchool().getId()).orElseThrow(
                () -> new IllegalArgumentException("School not found"));

        Map<String, Object> groupMap = prepareGroupMap(group);
        Map<String, Object> departmentMap = prepareDepartmentMap(department);
        Map<String, Object> schoolMap = prepareSchoolMap(school);

        return prepareGroupInfoMap(groupMap, departmentMap, schoolMap);
    }

    private Optional<Group> findGroup(String groupId) {
        return Optional.ofNullable(groupJpaRepository.findOne(groupId));
    }

    private Map<String, Object> prepareGroupMap(Group group) {
        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("id", group.getId());
        groupMap.put("name", group.getName());
        groupMap.put("members", group.getMembers());
        return groupMap;
    }

    private Map<String, Object> prepareDepartmentMap(Department department) {
        Map<String, Object> departmentMap = new HashMap<>();
        departmentMap.put("id", department.getId());
        departmentMap.put("name", department.getName());
        departmentMap.put("numberOfGroups", department.getNumberOfGroups());
        return departmentMap;
    }

    private Map<String, Object> prepareSchoolMap(School school) {
        Map<String, Object> schoolMap = new HashMap<>();
        schoolMap.put("id", school.getId());
        schoolMap.put("name", school.getName());
        schoolMap.put("numberOfDepartments", school.getNumberOfDepartments());
        return schoolMap;
    }

    private Map<String, Object> prepareGroupInfoMap(Map<String, Object> groupMap,
                                                    Map<String, Object> departmentMap,
                                                    Map<String, Object> schoolMap) {
        Map<String, Object> groupInfo = new HashMap<>();
        groupInfo.put("school", schoolMap);
        groupInfo.put("department", departmentMap);
        groupInfo.put("group", groupMap);
        return groupInfo;
    }

}
