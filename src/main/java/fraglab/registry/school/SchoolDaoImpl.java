package fraglab.registry.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SchoolDaoImpl implements SchoolDao {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public SchoolData fetchSchoolData(String yearClassId) {
        String years = "select new fraglab.registry.school.SchoolData(a.id, s.name, c.label, y.label) " +
                "from SchoolClassYearAggregation a join a.year y join a.clazz c join a.clazz.school s where a.id=:yearClassId " +
                "order by y.label";

        return (SchoolData) entityManager.createQuery(years).setParameter("yearClassId", yearClassId).getSingleResult();
    }

    @Override
    public void execute() {
        School kindergarten22 = new School("22ο Νέας Ιωνίας");
        SchoolClass classicGroup = new SchoolClass("Κλασσικό");
        kindergarten22.addClass(classicGroup);
        entityManager.persist(kindergarten22);

        SchoolYear term2012_2013 = new SchoolYear("2012-2013");
        SchoolYear term2013_2014 = new SchoolYear("2013-2014");
        SchoolYear term2014_2015 = new SchoolYear("2014-2015");

        entityManager.persist(term2012_2013);
        entityManager.persist(term2013_2014);
        entityManager.persist(term2014_2015);


        SchoolClassYearAggregation schoolClassYearAggregation1 = new SchoolClassYearAggregation();
        schoolClassYearAggregation1.setClazz(classicGroup);
        schoolClassYearAggregation1.setYear(term2012_2013);

        SchoolClassYearAggregation schoolClassYearAggregation2 = new SchoolClassYearAggregation();
        schoolClassYearAggregation2.setClazz(classicGroup);
        schoolClassYearAggregation2.setYear(term2013_2014);

        SchoolClassYearAggregation schoolClassYearAggregation3 = new SchoolClassYearAggregation();
        schoolClassYearAggregation3.setClazz(classicGroup);
        schoolClassYearAggregation3.setYear(term2014_2015);

        entityManager.persist(schoolClassYearAggregation1);
        entityManager.persist(schoolClassYearAggregation2);
        entityManager.persist(schoolClassYearAggregation3);

        entityManager.flush();
    }

    @Override
    public List<SchoolTreeElement> fetchSchoolTreeElements() {
        String schools = "select new fraglab.registry.school.SchoolTreeElement(s.id, s.name) " +
                "from School s " +
                "order by s.name";
        List<SchoolTreeElement> schoolNodes = (List<SchoolTreeElement>) entityManager.createQuery(schools).getResultList();

        String classes = "select new fraglab.registry.school.SchoolTreeElement(c.id, c.label, s.id) " +
                "from SchoolClass c join c.school s " +
                "order by c.label";
        List<SchoolTreeElement> classNodes = (List<SchoolTreeElement>) entityManager.createQuery(classes).getResultList();

        String years = "select new fraglab.registry.school.SchoolTreeElement(a.id, y.label, c.id) " +
                "from SchoolClassYearAggregation a join a.year y join a.clazz c " +
                "order by y.label";
        List<SchoolTreeElement> yearNodes = (List<SchoolTreeElement>) entityManager.createQuery(years).getResultList();

        for (SchoolTreeElement schoolNode : schoolNodes) {
            schoolNode.setType(SchoolTreeElement.Type.SCHOOL);
            System.out.println(schoolNode.getLabel());
            for (SchoolTreeElement classNode : classNodes) {
                classNode.setType(SchoolTreeElement.Type.CLASS);
                if (classNode.getParentId().equals(schoolNode.getId())) {
                    schoolNode.addChild(classNode);
                    System.out.println("\t" + classNode.getLabel());
                    for (SchoolTreeElement yearNode : yearNodes) {
                        yearNode.setType(SchoolTreeElement.Type.YEAR);
                        if (yearNode.getParentId().equals(classNode.getId())) {
                            classNode.addChild(yearNode);
                            System.out.println("\t\t" + yearNode.getLabel());
                        }
                    }
                }
            }
        }

        return schoolNodes;
    }

    @Override
    public List<School> fetchSchools() {
        LOG.debug("Fetching all schools");
        Query query = entityManager.createQuery("select s from School s");
        return query.getResultList();
    }

    @Override
    public void updateSchool(School school) {
        entityManager.merge(school);
    }

    @Override
    public List<SchoolClass> fetchClassesForSchool(String id) {
        LOG.debug("Fetching all classes for school [{}]", id);
        Query query = entityManager.createQuery("select c from SchoolClass c where c.school.id=:schoolId");
        query.setParameter("schoolId", id);
        return query.getResultList();
    }

    @Override
    public void updateClassForSchool(String id, SchoolClass schoolClass) {
        LOG.debug("Updating school [{}]: Adding class [{}].", id, schoolClass.getId());
        School school = entityManager.find(School.class, id);
        school.addClass(schoolClass);
        entityManager.merge(school);
    }

    @Override
    public List<SchoolYear> fetchYears() {
        LOG.debug("Fetching all school terms");
        Query query = entityManager.createQuery("select t from SchoolYear t order by label");
        return query.getResultList();
    }

    @Override
    public void updateYear(SchoolYear year) {
        entityManager.merge(year);
    }

}
