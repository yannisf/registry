package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"file:///home/yannis/development/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class SchoolDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SchoolDao schoolDao;

    @Test
    public void test() {
        schoolDao.execute();
    }

}
