package fraglab.registry.school;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.foundation.FoundationService;
import fraglab.registry.foundation.Group;
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

@ContextConfiguration(locations = {"file:///home/yannis/development/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class ChildTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ChildService childService;
    
    @Autowired
    private AddressService addressService;

    @Autowired
    private FoundationService foundationService;
    
    private String childId;
    
    private String addressId;

    private String groupId = "14fbd8de-9eb7-4487-bdee-b4412cec43a2";

    @Test
    @Transactional
    @Rollback(false)
    public void testAddChild() throws NotIdentifiedException {
        Group group = childService.fetchGroup(groupId);
        Child child = new Child();
        child.setId(generateUuid());
        child.setFirstName("Manolis");
        child.setLastName("Mitsia");
        group.addChild(child);
        childService.createOrUpdate(child);
        childId = child.getId();
    }

    @Test(dependsOnMethods = "testAddChild")
    @Transactional
    @Rollback(false)
    public void testAddAddress() {
        Address address = new Address();
        address.setId(generateUuid());
        address.setCity("Peiraias");
        addressService.createOrUpdate(address);
        addressId = address.getId();
    }

    @Test(dependsOnMethods = "testAddAddress")
    @Transactional
    @Rollback(false)
    public void testChildAddressBinding() throws NotFoundException {
        Child child = childService.fetch(childId);
        Address address = addressService.fetch(addressId);
        child.setAddress(address);
    }

//    @Test(dependsOnMethods = "testChildAddressBinding")
//    @Transactional
//    @Rollback(false)
    public void testAssertAndDeleteChildAndAddress() throws NotFoundException {
        Child child = childService.fetch(childId);
        String childAddressId = child.getAddress().getId();
        Assert.assertEquals(childAddressId, addressId);
        childService.delete(child.getId());
        addressService.delete(childAddressId);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
