package fraglab.registry.school;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.web.NotFoundException;
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
    
    private String childId;
    
    private String addressId;

    @Test
    @Transactional
    @Rollback(false)
    public void testAddChild() {
        Child child = new Child();
        child.setId(generateUuid());
        child.setFirstName("Giorgos");
        child.setLastName("Papadopoulos");
        childService.update(child);
        childId = child.getId();
    }

    @Test(dependsOnMethods = "testAddChild")
    @Transactional
    @Rollback(false)
    public void testAddAddress() {
        Address address = new Address();
        address.setId(generateUuid());
        address.setCity("Athens");
        addressService.update(address);
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

    @Test(dependsOnMethods = "testChildAddressBinding")
    @Transactional
//    @Rollback(false)
    public void testAssertAndDeleteChildAndAddress() throws NotFoundException {
        Child child = childService.fetch(childId);
        String childAddressId = child.getAddress().getId();
        Assert.assertEquals(childAddressId, addressId);
        childService.delete(child.getId());
        addressService.delete(childAddressId);
    }

    @Test(dependsOnMethods = "testAddAddress")
    @Transactional
    @Rollback(false)
    public void testAddChildToGroup() throws NotFoundException {
        Child child = childService.fetch("");
        Group group = childService.fetchGroup("ec59b434-5aaa-4861-8985-929e469d94dc");
        group.addChild(child);
        
        
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
