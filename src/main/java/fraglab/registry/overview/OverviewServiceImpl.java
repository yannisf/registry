package fraglab.registry.overview;

import fraglab.data.GenericDao;
import fraglab.registry.child.Child;
import fraglab.registry.overview.meta.GroupDataTransfer;
import fraglab.registry.overview.meta.GroupStatistics;
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
    private GenericDao dao;

    @Override
    public GroupDataTransfer fetchSchoolData(String groupId) {
        String schoolDataQuery = "select new fraglab.registry.overview.meta.GroupDataTransfer(" +
                "g.id, s.name, cr.name, g.name, g.members) " +
                "from Group g join g.department cr join g.department.school s " +
                "where g.id=:groupId order by g.name";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);

        return dao.findSingleByQuery(GroupDataTransfer.class, schoolDataQuery, params);
    }

    @Override
    public List<Child> fetchChildrenForGroup(String groupId) {
        String query = "select c from Child c where c.group.id=:groupId";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return  dao.findByQuery(Child.class, query, params);
    }

    @Override
    public List<String> fetchChildrenIdsForGroup(String groupId) {
        String query = "select c.id from Child c where c.group.id=:groupId";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return  dao.findByQuery(String.class, query, params);
    }

    @Override
    public GroupStatistics fetchChildGroupStatistics(String groupId) {
        String query = "select " +
                "BOYS.BOYS_NUMBER, " +
                "GIRLS.GIRLS_NUMBER, " +
                "PRESCHOOL_LEVEL_A.PRESCHOOL_LEVEL_A_NUMBER, " +
                "PRESCHOOL_LEVEL_B.PRESCHOOL_LEVEL_B_NUMBER from " +
                "(select count(*) AS BOYS_NUMBER from Person p " +
                "where p.group_id = :groupId and p.gender = 'MALE') BOYS, " +
                "(select count(*) AS GIRLS_NUMBER from Person p " +
                "where p.group_id = :groupId and p.gender = 'FEMALE') GIRLS, " +
                "(select count(*) AS PRESCHOOL_LEVEL_A_NUMBER from Person p " +
                "where p.group_id = :groupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_A') PRESCHOOL_LEVEL_A, " +
                "(select count(*)AS PRESCHOOL_LEVEL_B_NUMBER from Person p " +
                "where p.group_id = :groupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_B') PRESCHOOL_LEVEL_B ";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        Object[] result = dao.findSingleByNativeQuery(query, params);

        return new GroupStatistics(groupId,
                ((BigInteger) result[0]).intValue(),
                ((BigInteger) result[1]).intValue(),
                ((BigInteger) result[2]).intValue(),
                ((BigInteger) result[3]).intValue());
    }

    @Override
    public void createOrUpdateSchool(School school) {
        dao.createOrUpdate(school);
    }

    @Override
    public void createOrUpdateDepartment(Department department) {
        dao.createOrUpdate(department);
    }

    @Override
    public void createOrUpdateGroup(Group group) {
        dao.createOrUpdate(group);
    }

    @Override
    public List<School> fetchSchools() {
        return dao.findByQuery(School.class, "select s from School s order by s.name");
    }

    @Override
    public School fetchSchool(String id) throws NotFoundException {
        School school = dao.fetch(School.class, id);
        if (school == null) {
            throw new NotFoundException();
        }

        return school;
    }

    @Override
    public Department fetchDepartment(String id) throws NotFoundException {
        Department department = dao.fetch(Department.class, id);
        if (department == null) {
            throw new NotFoundException();
        }

        return department;
    }

    @Override
    public List<Department> fetchDepartmentsForSchool(String schoolId) {
        String query = "select c from Department c where c.school.id=:schoolId order by c.name";
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        return  dao.findByQuery(Department.class, query, params);
    }

    @Override
    public void deleteSchool(String id) {
        School school;
        try {
            school = fetchSchool(id);
            dao.delete(school);
        } catch (NotFoundException e) {
            LOG.info("Record does not exist", e);
        }
    }

    @Override
    public void createOrUpdateDepartmentForSchool(String schoolId, Department department) throws NotFoundException {
        School school = fetchSchool(schoolId);
        school.addDepartment(department);
        dao.createOrUpdate(department);
    }

    @Override
    public List<Group> fetchGroupsForDepartment(String departmentId) throws NotFoundException {
        Department department = fetchDepartment(departmentId);
        department.getGroups().size();
        return department.getGroups();
    }

    @Override
    public void deleteDepartment(String departmentId){
        Department department = null;
        try {
            department = fetchDepartment(departmentId);
            dao.delete(department);
        } catch (NotFoundException e) {
            LOG.info("Department [{}] not found. ", departmentId);
        }
    }

    @Override
    public void createOrUpdateGroupForDepartment(Group group, String departmentId) throws NotFoundException {
        if (departmentId != null) {
            Department department = fetchDepartment(departmentId);
            group.setDepartment(department);
        }
        dao.createOrUpdate(group);
    }

    @Override
    public void deleteGroup(String id) {
        try {
            Group group = fetchGroup(id);
            dao.delete(group);
        } catch (NotFoundException e) {
            LOG.info("Group [{}] not found. ", id);
        }
    }

    @Override
    public Map<String, Object> fetchGroupInfo(String groupId) throws NotFoundException {
        Group group = fetchGroup(groupId);
        Department department = fetchDepartment(group.getDepartment().getId());
        School school = fetchSchool(department.getSchool().getId());

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
        Group group = dao.fetch(Group.class, groupId);
        if (group == null) {
            throw new NotFoundException("Group " + groupId + " not found. ");
        }
        return group;
    }

}
