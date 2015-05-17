package fraglab.registry.school;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.overview.OverviewService;
import fraglab.registry.overview.Group;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.UUID;

@ContextConfiguration(locations = {"file:///C:/local/workspace/misc/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class PersistenceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private OverviewService overviewService;
    
    @Test
    @Transactional
    @Rollback(false)
    public void testOverviewService() {
        //noop
    }

}
