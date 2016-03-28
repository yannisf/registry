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
import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OverviewServiceImpl implements OverviewService {

    private static final Logger LOG = LoggerFactory.getLogger(OverviewServiceImpl.class);

    @Autowired
    private ChildJpaRepository childJpaRepository;

    @Autowired
    private SchoolJpaRepository schoolJpaRepository;

    @Autowired
    private DepartmentJpaRepository departmentJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Override
    public GroupDataTransfer fetchSchoolData(String groupId) {
        return groupJpaRepository.queryByGroupForMetadata(groupId);
    }

    @Override
    public List<Child> findChildrenForGroup(String groupId) {
        return  childJpaRepository.queryForGroup(groupId);
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
    public void createOrUpdateSchool(School school) {
        schoolJpaRepository.save(school);
    }

    @Override
    public void createOrUpdateDepartment(Department department) {
        departmentJpaRepository.save(department);
    }

    @Override
    public void createOrUpdateGroup(Group group) {
        groupJpaRepository.save(group);
    }

    @Override
    public List<School> fetchSchools() {
        return schoolJpaRepository.findAllByOrderByNameAsc();
    }

    @Override
    public School findSchool(String id) throws NotFoundException {
        School school = schoolJpaRepository.findOne(id);
        if (school == null) {
            throw new NotFoundException();
        }

        return school;
    }

    @Override
    public Department findDepartment(String id) throws NotFoundException {
        Department department = departmentJpaRepository.findOne(id);
        if (department == null) {
            throw new NotFoundException();
        }

        return department;
    }

    @Override
    public List<Department> findDepartmentsForSchool(String schoolId) {
        return  departmentJpaRepository.queryBySchoolId(schoolId);
    }

    @Override
    public void deleteSchool(String id) {
        School school;
        try {
            school = findSchool(id);
            schoolJpaRepository.delete(school);
        } catch (NotFoundException e) {
            LOG.info("Record does not exist", e);
        }
    }

    @Override
    public void saveDepartmentForSchool(String schoolId, Department department) throws NotFoundException {
        School school = findSchool(schoolId);
        school.addDepartment(department);
        departmentJpaRepository.save(department);
    }

    @Override
    public List<Group> findGroupsForDepartment(String departmentId) throws NotFoundException {
        Department department = findDepartment(departmentId);
        department.getGroups().size();
        return department.getGroups();
    }

    @Override
    public void deleteDepartment(String departmentId){
        departmentJpaRepository.delete(departmentId);
    }

    @Override
    public void saveGroupForDepartment(Group group, String departmentId) throws NotFoundException {
        if (departmentId != null) {
            Department department = findDepartment(departmentId);
            group.setDepartment(department);
        }
        groupJpaRepository.save(group);
    }

    @Override
    public void deleteGroup(String id) {
        try {
            Group group = fetchGroup(id);
            groupJpaRepository.delete(group);
        } catch (NotFoundException e) {
            LOG.info("Group [{}] not found. ", id);
        }
    }

    @Override
    public Map<String, Object> fetchGroupInfo(String groupId) throws NotFoundException {
        Group group = fetchGroup(groupId);
        Department department = findDepartment(group.getDepartment().getId());
        School school = findSchool(department.getSchool().getId());

        Map<String, Object> groupMap = prepareGroupMap(group);
        Map<String, Object> departmentMap = prepareDepartmentMap(department);
        Map<String, Object> schoolMap = prepareSchoolMap(school);

        return  prepareGroupInfoMap(groupMap, departmentMap, schoolMap);
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

    private Group fetchGroup(String groupId) throws NotFoundException {
        Group group = groupJpaRepository.findOne(groupId);
        if (group == null) {
            throw new NotFoundException("Group " + groupId + " not found. ");
        }
        return group;
    }

}
