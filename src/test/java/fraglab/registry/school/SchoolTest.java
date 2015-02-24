package fraglab.registry.school;

import fraglab.registry.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.UUID;

@ContextConfiguration(locations = {"file:///home/yannis/development/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class SchoolTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private ChildService childService;
    
    private String schoolId;
    private String classroomId1;
    private String classroomId2;
    private String termId1;
    private String termId2;
    private String groupId;

    @Test
    @Transactional
    @Rollback(false)
    public void testAddSchoolSystem() {
        School school = new School("My school");
        school.setId(generateUuid());
        schoolId = school.getId();
        
        Classroom classroom1 = new Classroom("My classroom 1");
        classroom1.setId(generateUuid());
        classroomId1 = classroom1.getId();
        
        Classroom classroom2 = new Classroom("My classroom 2");
        classroom2.setId(generateUuid());
        classroomId2 = classroom2.getId();
        
        school.addClassroom(classroom1);
        school.addClassroom(classroom2);
        schoolService.update(school);
        
        Term term1 = new Term("2013-2014");
        term1.setId(generateUuid());
        termId1 = term1.getId();
        schoolService.createOrUpdateTerm(term1);
        
        Term term2 = new Term("2014-2015");
        term2.setId(generateUuid());
        termId2 = term2.getId();
        schoolService.createOrUpdateTerm(term2);
        
        Group group = new Group();
        group.setId(generateUuid());
        groupId = group.getId();
        group.setClassroom(classroom2);
        group.setTerm(term1);
        schoolService.createOrUpdateGroup(group);
    }

    @Test(dependsOnMethods = "testAddSchoolSystem")
    @Transactional
    @Rollback(false)
    public void testSchoolSystem() {
        Group group = childService.fetchGroup(groupId);
        Assert.assertEquals(group.getClassroom().getId(), classroomId2);
        Assert.assertEquals(group.getClassroom().getSchool().getId(), schoolId);
        Assert.assertEquals(group.getTerm().getId(), termId1);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
