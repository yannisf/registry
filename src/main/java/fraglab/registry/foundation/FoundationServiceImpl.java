package fraglab.registry.foundation;

import fraglab.data.GenericDao;
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
        String schoolDataQuery = "select new fraglab.registry.school.SchoolData(cg.id, s.name, cr.name, t.name, cg.members) " +
                "from Group g join g.term t join g.classroom cr join g.classroom.school s " +
                "where g.id=:grouId order by t.name";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);

        return (GroupDataTransfer) dao.findByQuery(GroupDataTransfer.class, schoolDataQuery, params).get(0);
    }

    @Override
    public GroupStatistics fetchChildGroupStatistics(String groupId) {
        String query = "select " +
                "BOYS.BOYS_NUMBER, " +
                "GIRLS.GIRLS_NUMBER, " +
                "PRESCHOOL_LEVEL_A.PRESCHOOL_LEVEL_A_NUMBER, " +
                "PRESCHOOL_LEVEL_B.PRESCHOOL_LEVEL_B_NUMBER from " +
                "(select count(*) AS BOYS_NUMBER from person p " +
                "where p.child_group_id = :childGroupId and p.genre = 'MALE') BOYS, " +
                "(select count(*) AS GIRLS_NUMBER from person p " +
                "where p.child_group_id = :childGroupId and p.genre = 'FEMALE') GIRLS, " +
                "(select count(*) AS PRESCHOOL_LEVEL_A_NUMBER from person p " +
                "where p.child_group_id = :childGroupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_A') PRESCHOOL_LEVEL_A, " +
                "(select count(*)AS PRESCHOOL_LEVEL_B_NUMBER from person p " +
                "where p.child_group_id = :childGroupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_B') PRESCHOOL_LEVEL_B ";

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
        String query = "select new fraglab.registry.school.TreeElement(s.id, s.name) from School s order by s.name";
        return dao.findByQuery(query);
    }

    private List<TreeElement> getClassroomNodes() {
        String query = "select new fraglab.registry.school.TreeElement(cr.id, cr.name, s.id) " +
                "from Classroom cr join cr.school s order by cr.name";
        return dao.findByQuery(query);
    }

    private List<TreeElement> getTermNodes() {
        String query = "select new fraglab.registry.school.TreeElement(cg.id, t.name, cr.id, cg.members) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr order by t.name";
        return dao.findByQuery(query);
    }

}
