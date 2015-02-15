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
    public SchoolData fetchSchoolData(String childGroupId) {
        String years = "select new fraglab.registry.school.SchoolData(cg.id, s.name, cr.name, t.name) " +
                "from ChildGroup cg join cg.term t join cg.classroom cr join cg.classroom.school s " +
                "where cg.id=:childGrouId order by t.name";

        return (SchoolData) entityManager.createQuery(years).setParameter("childGrouId", childGroupId).getSingleResult();
    }

    @Override
    public void init() {
        School kindergarten22 = new School("22ο Νέας Ιωνίας");
        Classroom classicGroup = new Classroom("Κλασσικό");
        kindergarten22.addClassroom(classicGroup);
        entityManager.persist(kindergarten22);

        Term term2012_2013 = new Term("2012-2013");
        Term term2013_2014 = new Term("2013-2014");
        Term term2014_2015 = new Term("2014-2015");

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
    public List<TreeElement> fetchSchoolTreeElements() {
        List<TreeElement> schoolNodes = getSchoolNodes();
        List<TreeElement> classroomNodes = getClassroomNodes();
        List<TreeElement> termNodes = getTermNodes();

        for (TreeElement schoolNode : schoolNodes) {
            schoolNode.setType(TreeElement.Type.SCHOOL);
            System.out.println(schoolNode.getName());
            for (TreeElement classroomNode : classroomNodes) {
                classroomNode.setType(TreeElement.Type.CLASSROOM);
                if (classroomNode.getParentId().equals(schoolNode.getId())) {
                    schoolNode.addChild(classroomNode);
                    System.out.println("\t" + classroomNode.getName());
                    for (TreeElement termNode : termNodes) {
                        termNode.setType(TreeElement.Type.TERM);
                        if (termNode.getParentId().equals(classroomNode.getId())) {
                            classroomNode.addChild(termNode);
                            System.out.println("\t\t" + termNode.getName());
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

    @Override
    public List<School> fetchSchools() {
        LOG.debug("Fetching all schools");
        TypedQuery<School> query = entityManager.createQuery("select s from School s", School.class);
        return query.getResultList();
    }

    @Override
    public void updateSchool(School school) {
        entityManager.merge(school);
    }

    @Override
    public List<Classroom> fetchClassroomsForSchool(String id) {
        LOG.debug("Fetching all classes for school [{}]", id);
        TypedQuery<Classroom> query = entityManager.createQuery("select cr from Classroom cr where cr.school.id=:schoolId", Classroom.class);
        query.setParameter("schoolId", id);
        return query.getResultList();
    }

    @Override
    public void updateClassroomForSchool(String id, Classroom classroom) {
        LOG.debug("Updating school [{}]: Adding class [{}].", id, classroom.getId());
        School school = entityManager.find(School.class, id);
        school.addClassroom(classroom);
        entityManager.merge(school);
    }

    @Override
    public List<Term> fetchTerms() {
        LOG.debug("Fetching all terms");
        TypedQuery<Term> query = entityManager.createQuery("select t from Term t order by name", Term.class);
        return query.getResultList();
    }

    @Override
    public void updateTerm(Term year) {
        entityManager.merge(year);
    }

}
