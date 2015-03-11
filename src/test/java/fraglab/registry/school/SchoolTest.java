package fraglab.registry.school;

import fraglab.registry.child.ChildService;
import fraglab.registry.foundation.Classroom;
import fraglab.registry.foundation.FoundationService;
import fraglab.registry.foundation.Group;
import fraglab.registry.foundation.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.UUID;

@ContextConfiguration(locations = {"file:///C:/local/workspace/misc/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class SchoolTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private FoundationService foundationService;
    
    @Autowired
    private ChildService childService;
    
    private String schoolId;
    private String classroomId1;
    private String classroomId2;
    private String groupId;

    @Test
    @Transactional
    @Rollback(false)
    public void testAddSchoolSystem() {
        School school = new School("My school");
        school.setId(generateUuid());
        schoolId = school.getId();
        foundationService.createOrUpdateSchool(school);
        
        Classroom classroom1 = new Classroom("My classroom 1");
        classroom1.setId(generateUuid());
        classroomId1 = classroom1.getId();
        classroom1.setSchool(school);
        foundationService.createOrUpdateClassroom(classroom1);
        
        Classroom classroom2 = new Classroom("My classroom 2");
        classroom2.setId(generateUuid());
        classroomId2 = classroom2.getId();
        classroom2.setSchool(school);
        foundationService.createOrUpdateClassroom(classroom2);

        Group group = new Group("2013-2014");
        group.setId(generateUuid());
        groupId = group.getId();
        group.setClassroom(classroom2);
        foundationService.createOrUpdateGroup(group);
    }

    @Test(dependsOnMethods = "testAddSchoolSystem")
    @Transactional
    @Rollback(false)
    public void testSchoolSystem() {
        Group group = childService.fetchGroup(groupId);
        Assert.assertEquals(group.getClassroom().getId(), classroomId2);
        Assert.assertEquals(group.getClassroom().getSchool().getId(), schoolId);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
