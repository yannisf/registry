package fraglab.registry.school;

import fraglab.registry.child.ChildService;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"file:///home/yannis/development/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class ChildTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ChildService childService;

    private Client client;

    @BeforeClass
    public void init() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("node.name", "Buster")
                .put("cluster.name", "fraglab")
                .build();

        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("192.168.1.3", 9300));

//        node = NodeBuilder.nodeBuilder().settings(settings).node();
        System.out.println("Built client");
    }

    @AfterClass
    public void tearDown() {
        client.close();
    }

}
