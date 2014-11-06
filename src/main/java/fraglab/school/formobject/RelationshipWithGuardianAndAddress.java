package fraglab.school.formobject;

import fraglab.school.address.Address;
import fraglab.school.guardian.Guardian;
import fraglab.school.relationship.ChildGuardianRelationship;

import java.io.Serializable;

public class RelationshipWithGuardianAndAddress implements Serializable {

    private ChildGuardianRelationship relationship;
    private Guardian guardian;
    private Address address;

    public ChildGuardianRelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ChildGuardianRelationship relationship) {
        this.relationship = relationship;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
