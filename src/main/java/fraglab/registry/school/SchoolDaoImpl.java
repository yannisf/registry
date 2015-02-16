package fraglab.registry.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SchoolDaoImpl implements SchoolDao {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void init() {
        School kindergarten22 = new School("22ο Νηπιαγωγείο Νέας Ιωνίας");
        Classroom classicGroup = new Classroom("Κλασσικό Τμήμα");
        kindergarten22.addClassroom(classicGroup);
        entityManager.persist(kindergarten22);

        Term term2012_2013 = new Term("2012 - 2013");
        Term term2013_2014 = new Term("2013 - 2014");
        Term term2014_2015 = new Term("2014 - 2015");

        entityManager.persist(term2012_2013);
        entityManager.persist(term2013_2014);
        entityManager.persist(term2014_2015);


        ChildGroup childGroup1 = new ChildGroup();
        childGroup1.setClassroom(classicGroup);
        childGroup1.setTerm(term2012_2013);

        ChildGroup childGroup2 = new ChildGroup();
        childGroup2.setClassroom(classicGroup);
        childGroup2.setTerm(term2013_2014);

        ChildGroup childGroup3 = new ChildGroup();
        childGroup3.setClassroom(classicGroup);
        childGroup3.setTerm(term2014_2015);

        entityManager.persist(childGroup1);
        entityManager.persist(childGroup2);
        entityManager.persist(childGroup3);

        entityManager.flush();
    }

    @Override
    public SchoolData fetchSchoolData(String childGroupId) {
        String schoolDataQuery = "select new fraglab.registry.school.SchoolData(cg.id, s.name, cr.name, t.name) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr join cg.classroom.school s " +
                "where cg.id=:childGrouId order by t.name";

        return (SchoolData) entityManager.createQuery(schoolDataQuery).setParameter("childGrouId", childGroupId).getSingleResult();
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
        String schools = "select new fraglab.registry.school.TreeElement(s.id, s.name) from School s order by s.name";
        return getNodes(schools);
    }

    private List<TreeElement> getClassroomNodes() {
        String classrooms = "select new fraglab.registry.school.TreeElement(cr.id, cr.name, s.id) " +
                "from Classroom cr join cr.school s order by cr.name";
        return getNodes(classrooms);
    }

    private List<TreeElement> getTermNodes() {
        String terms = "select new fraglab.registry.school.TreeElement(cg.id, t.name, cr.id) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr order by t.name";
        return getNodes(terms);
    }

    private List<TreeElement> getNodes(String query) {
        TypedQuery<TreeElement> termsQuery = entityManager.createQuery(query, TreeElement.class);
        return termsQuery.getResultList();
    }

}
