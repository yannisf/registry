package fraglab.registry.foundation;

import fraglab.data.GenericDao;
import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;
import fraglab.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoundationServiceImpl implements FoundationService {

    private static final Logger LOG = LoggerFactory.getLogger(FoundationServiceImpl.class);

    @Autowired
    private GenericDao dao;

    @Override
    public GroupDataTransfer fetchSchoolData(String groupId) {
        String schoolDataQuery = "select new fraglab.registry.foundation.meta.GroupDataTransfer(" +
                "g.id, s.name, cr.name, g.name, g.members) " +
                "from Group g join g.classroom cr join g.classroom.school s " +
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
                "(select count(*) AS BOYS_NUMBER from person p " +
                "where p.group_id = :groupId and p.genre = 'MALE') BOYS, " +
                "(select count(*) AS GIRLS_NUMBER from person p " +
                "where p.group_id = :groupId and p.genre = 'FEMALE') GIRLS, " +
                "(select count(*) AS PRESCHOOL_LEVEL_A_NUMBER from person p " +
                "where p.group_id = :groupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_A') PRESCHOOL_LEVEL_A, " +
                "(select count(*)AS PRESCHOOL_LEVEL_B_NUMBER from person p " +
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
    public void createOrUpdateClassroom(Classroom classroom) {
        dao.createOrUpdate(classroom);
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
    public List<Classroom> fetchClassroomsForSchool(String schoolId) {
        String query = "select c from Classroom c where c.school.id=:schoolId order by c.name";
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        return  dao.findByQuery(Classroom.class, query, params);
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
    public void createOrUpdateClassroomForSchool(String schoolId, Classroom classroom) {
        //TODO:
    }


    @Override
    public List<TreeElement> fetchSchoolTreeElements() {
        List<TreeElement> schoolNodes = getSchoolNodes();
        List<TreeElement> classroomNodes = getClassroomNodes();
        List<TreeElement> groupNodes = getGroupNodes();

        for (TreeElement schoolNode : schoolNodes) {
            schoolNode.setType(TreeElement.Type.SCHOOL);
            for (TreeElement classroomNode : classroomNodes) {
                classroomNode.setType(TreeElement.Type.CLASSROOM);
                if (classroomNode.getParentId().equals(schoolNode.getId())) {
                    schoolNode.addChild(classroomNode);
                    for (TreeElement groupNode : groupNodes) {
                        groupNode.setType(TreeElement.Type.GROUP);
                        if (groupNode.getParentId().equals(classroomNode.getId())) {
                            classroomNode.addChild(groupNode);
                        }
                    }
                }
            }
        }

        return schoolNodes;
    }

    private List<TreeElement> getSchoolNodes() {
        String query = "select new fraglab.registry.foundation.meta.TreeElement(s.id, s.name) from School s order by s.name";
        return dao.findByQuery(TreeElement.class, query);
    }

    private List<TreeElement> getClassroomNodes() {
        String query = "select new fraglab.registry.foundation.meta.TreeElement(cr.id, cr.name, s.id) " +
                "from Classroom cr join cr.school s order by cr.name";
        return dao.findByQuery(TreeElement.class, query);
    }

    private List<TreeElement> getGroupNodes() {
        String query = "select new fraglab.registry.foundation.meta.TreeElement(g.id, g.name, cr.id, g.members) " +
                "from Group g join g.classroom cr order by g.name";
        return dao.findByQuery(TreeElement.class, query);
    }

}
