package fraglab.registry;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dispatcher-servlet.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@ActiveProfiles("dev")
public class IntegrationTest {

    @Autowired
    private ChildService service;

    @Test
    @Commit
    public void test1() {
        System.out.println("test1");
        Child child = new Child();
        child.setId("child");
        child.setLastName("Last");
        service.save(child);
    }

    @Test
    @Commit
    public void test2() throws IOException {
        System.out.println("test2");
        service.saveChildPhoto("child", IOUtils.toByteArray(new FileInputStream("/home/yannis/Pictures/kid.jpg")));
    }

    @Test
    @Commit
    public void test3() {
        System.out.println("test3");
        Child child = new Child();
        child.setId("child");
        child.setLastName("XLast");
        service.save(child);
        service.deleteChildPhoto("child");
    }


}
