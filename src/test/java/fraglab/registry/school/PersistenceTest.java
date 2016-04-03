package fraglab.registry.school;

import fraglab.registry.overview.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import javax.transaction.Transactional;

//@ContextConfiguration(locations = {"file:///C:/local/workspace/misc/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class PersistenceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private OverviewService overviewService;
    
//    @Test
    @Transactional
    @Rollback(false)
    public void testOverviewService() {
        //noop
    }

}
