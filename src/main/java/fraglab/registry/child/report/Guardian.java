package fraglab.registry.child.report;

import fraglab.registry.common.Telephone;
import fraglab.registry.relationship.Relationship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guardian implements Serializable {

    private String name;
    private Relationship.Type relationship;
    private boolean pickup;
    private List<Telephone> telephones;

    public Guardian() {
    }

    public Guardian(String name, Relationship.Type relationship, boolean pickup) {
        this.name = name;
        this.relationship = relationship;
        this.pickup = pickup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Relationship.Type getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship.Type relationship) {
        this.relationship = relationship;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void addTelephone(String number, Telephone.Type type) {
        if (this.telephones == null) {
            this.telephones = new ArrayList<>();
        }
        this.telephones.add(new Telephone(number, type));
    }

}
