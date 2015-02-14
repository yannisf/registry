package fraglab.registry.formobject;

import fraglab.registry.address.Address;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.relationship.Relationship;

import java.io.Serializable;

public class RelationshipWithGuardianAndAddress implements Serializable {

    private Relationship relationship;
    private Guardian guardian;
    private Address address;

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
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
