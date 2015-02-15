package fraglab.registry.child.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guardian implements Serializable {

    private String name;
    private String relationship;
    private boolean pickup;
    private List<Telephone> telephones;

    public Guardian() {
    }

    public Guardian(String name, String relationship, boolean pickup) {
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
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

    public void addTelephone(String number, String type) {
        if (this.telephones == null) {
            this.telephones = new ArrayList<>();
        }
        this.telephones.add(new Telephone(number, type));
    }

}
