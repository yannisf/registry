package fraglab.registry.school;

import fraglab.registry.child.ChildService;
import fraglab.registry.overview.Department;
import fraglab.registry.overview.OverviewService;
import fraglab.registry.overview.Group;
import fraglab.registry.overview.School;
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
    private OverviewService overviewService;
    
    @Autowired
    private ChildService childService;
    
    private String schoolId;
    private String departmentId1;
    private String departmentId2;
    private String groupId;

    @Test
    @Transactional
    @Rollback(false)
    public void testAddSchoolSystem() {
        School school = new School("My school");
        school.setId(generateUuid());
        schoolId = school.getId();
        overviewService.createOrUpdateSchool(school);
        
        Department department1 = new Department("My department 1");
        department1.setId(generateUuid());
        departmentId1 = department1.getId();
        department1.setSchool(school);
        overviewService.createOrUpdateDepartment(department1);
        
        Department department2 = new Department("My department 2");
        department2.setId(generateUuid());
        departmentId2 = department2.getId();
        department2.setSchool(school);
        overviewService.createOrUpdateDepartment(department2);

        Group group = new Group("2013-2014");
        group.setId(generateUuid());
        groupId = group.getId();
        group.setDepartment(department2);
        overviewService.createOrUpdateGroup(group);
    }

    @Test(dependsOnMethods = "testAddSchoolSystem")
    @Transactional
    @Rollback(false)
    public void testSchoolSystem() {
        Group group = childService.fetchGroup(groupId);
        Assert.assertEquals(group.getDepartment().getId(), departmentId2);
        Assert.assertEquals(group.getDepartment().getSchool().getId(), schoolId);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
