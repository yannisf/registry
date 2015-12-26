package fraglab.registry.address;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AddressTest {

    @Test
    public void addressEntityTest() {
        Address address = new Address();
        address.setCity("Athens");
        Assert.assertEquals(address.getCity(), "Athens");
    }

}
