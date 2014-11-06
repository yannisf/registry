package fraglab.school.formobject;

import fraglab.school.address.Address;
import fraglab.school.child.Child;

import java.io.Serializable;

public class ChildWithAddress implements Serializable {

    private Child child;
    private Address address;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
