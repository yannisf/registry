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
        School school3 = new School("3ο Νέας Ιωνίας");
        School school22 = new School("22ο Νέας Ιωνίας");
        SchoolClass schoolClass1 = new SchoolClass("Κλασσικό");
        SchoolClass schoolClass2 = new SchoolClass("Ολοήμερο");
        SchoolClass schoolClass3 = new SchoolClass("Κλασσικό");
        school3.addClass(schoolClass1);
        school3.addClass(schoolClass2);
        school22.addClass(schoolClass3);

        entityManager.persist(school3);
        entityManager.persist(school22);

        SchoolYear schoolYear1 = new SchoolYear("2012-2013");
        SchoolYear schoolYear2 = new SchoolYear("2013-2014");
        SchoolYear schoolYear3 = new SchoolYear("2014-2015");

        entityManager.persist(schoolYear1);
        entityManager.persist(schoolYear2);
        entityManager.persist(schoolYear3);

        SchoolClassYearAggregation schoolClassYearAggregation1 = new SchoolClassYearAggregation();
        schoolClassYearAggregation1.setClazz(schoolClass1);
        schoolClassYearAggregation1.setYear(schoolYear1);

        SchoolClassYearAggregation schoolClassYearAggregation2 = new SchoolClassYearAggregation();
        schoolClassYearAggregation2.setClazz(schoolClass1);
        schoolClassYearAggregation2.setYear(schoolYear2);

        SchoolClassYearAggregation schoolClassYearAggregation3 = new SchoolClassYearAggregation();
        schoolClassYearAggregation3.setClazz(schoolClass2);
        schoolClassYearAggregation3.setYear(schoolYear1);

        SchoolClassYearAggregation schoolClassYearAggregation4 = new SchoolClassYearAggregation();
        schoolClassYearAggregation4.setClazz(schoolClass2);
        schoolClassYearAggregation4.setYear(schoolYear2);

        SchoolClassYearAggregation schoolClassYearAggregation5 = new SchoolClassYearAggregation();
        schoolClassYearAggregation5.setClazz(schoolClass3);
        schoolClassYearAggregation5.setYear(schoolYear1);

        SchoolClassYearAggregation schoolClassYearAggregation6 = new SchoolClassYearAggregation();
        schoolClassYearAggregation6.setClazz(schoolClass3);
        schoolClassYearAggregation6.setYear(schoolYear2);

        SchoolClassYearAggregation schoolClassYearAggregation7 = new SchoolClassYearAggregation();
        schoolClassYearAggregation7.setClazz(schoolClass3);
        schoolClassYearAggregation7.setYear(schoolYear3);

        entityManager.persist(schoolClassYearAggregation1);
        entityManager.persist(schoolClassYearAggregation2);
        entityManager.persist(schoolClassYearAggregation3);
        entityManager.persist(schoolClassYearAggregation4);
        entityManager.persist(schoolClassYearAggregation5);
        entityManager.persist(schoolClassYearAggregation6);
        entityManager.persist(schoolClassYearAggregation7);

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
    public List<SchoolClass> fetchClassesForSchool(String id) {
        LOG.debug("Fetching all classes for school [{}]", id);
        Query query = entityManager.createQuery("select c from SchoolClass c where c.school.id=:schoolId");
        query.setParameter("schoolId", id);
        return query.getResultList();
    }

}
