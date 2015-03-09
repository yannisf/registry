package fraglab.registry.foundation;

import fraglab.data.GenericDao;
import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoundationServiceImpl implements FoundationService {

    @Autowired
    private GenericDao dao;

    @Override
    public GroupDataTransfer fetchSchoolData(String groupId) {
        String schoolDataQuery = "select new fraglab.registry.foundation.meta.GroupDataTransfer(g.id, s.name, cr.name, t.name, g.members) " +
                "from Group g join g.term t join g.classroom cr join g.classroom.school s " +
                "where g.id=:groupId order by t.name";
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
    public void createOrUpdateTerm(Term term) {
        dao.createOrUpdate(term);
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
    public List<Classroom> fetchClassroomsForSchool(String schoolId) {
        String query = "select c from Classroom c where c.school.id=:schoolId order by c.name";
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        return  dao.findByQuery(Classroom.class, query, params);
    }


    @Override
    public List<TreeElement> fetchSchoolTreeElements() {
        List<TreeElement> schoolNodes = getSchoolNodes();
        List<TreeElement> classroomNodes = getClassroomNodes();
        List<TreeElement> termNodes = getTermNodes();

        for (TreeElement schoolNode : schoolNodes) {
            schoolNode.setType(TreeElement.Type.SCHOOL);
            for (TreeElement classroomNode : classroomNodes) {
                classroomNode.setType(TreeElement.Type.CLASSROOM);
                if (classroomNode.getParentId().equals(schoolNode.getId())) {
                    schoolNode.addChild(classroomNode);
                    for (TreeElement termNode : termNodes) {
                        termNode.setType(TreeElement.Type.TERM);
                        if (termNode.getParentId().equals(classroomNode.getId())) {
                            classroomNode.addChild(termNode);
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

    private List<TreeElement> getTermNodes() {
        String query = "select new fraglab.registry.foundation.meta.TreeElement(g.id, t.name, cr.id, g.members) " +
                "from Group g join g.term t join g.classroom cr order by t.name";
        return dao.findByQuery(TreeElement.class, query);
    }

}
