package fraglab.registry.school;

import fraglab.data.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class SchoolDaoImpl extends GenericDaoImpl<School, String> implements SchoolDao {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @Override
    public SchoolData fetchSchoolData(String childGroupId) {
        String schoolDataQuery = "select new fraglab.registry.school.SchoolData(cg.id, s.name, cr.name, t.name, cg.members) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr join cg.classroom.school s " +
                "where cg.id=:childGrouId order by t.name";

        return (SchoolData) entityManager.createQuery(schoolDataQuery).setParameter("childGrouId", childGroupId).getSingleResult();
    }

    @Override
    public List<TreeElement> fetchSchoolTreeElements() {
        fetchClassroomStatistics("7c02192a-24c4-443e-ba76-dc5945896124");
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
        String schools = "select new fraglab.registry.school.TreeElement(s.id, s.name) from School s order by s.name";
        return getNodes(schools);
    }

    private List<TreeElement> getClassroomNodes() {
        String classrooms = "select new fraglab.registry.school.TreeElement(cr.id, cr.name, s.id) " +
                "from Classroom cr join cr.school s order by cr.name";
        return getNodes(classrooms);
    }

    private List<TreeElement> getTermNodes() {
        String terms = "select new fraglab.registry.school.TreeElement(cg.id, t.name, cr.id, cg.members) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr order by t.name";
        return getNodes(terms);
    }

    private List<TreeElement> getNodes(String query) {
        TypedQuery<TreeElement> termsQuery = entityManager.createQuery(query, TreeElement.class);
        return termsQuery.getResultList();
    }

    @Override
    public ChildGroupStatistics fetchClassroomStatistics(String childGroupId) {
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

        Object[] result = (Object[]) entityManager.createNativeQuery(query).setParameter("childGroupId", childGroupId).getSingleResult();

        return new ChildGroupStatistics(childGroupId, ((BigInteger) result[0]).intValue(),
                ((BigInteger) result[1]).intValue(), ((BigInteger) result[2]).intValue(), ((BigInteger) result[3]).intValue());
    }

    @Override
    public void createOrUpdateTerm(Term term) {
        entityManager.merge(term);
    }

    @Override
    public void createOrUpdateClassroom(Classroom classroom) {
        entityManager.merge(classroom);
    }

    @Override
    public void createOrUpdateGroup(Group group) {
        entityManager.merge(group);
    }

}
