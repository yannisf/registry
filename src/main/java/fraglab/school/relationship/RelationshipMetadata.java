package fraglab.school.relationship;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class RelationshipMetadata implements Serializable {

    @Enumerated(EnumType.STRING)
    private ChildGuardianRelationship.Type type;

    private String notes;

    private Boolean pickup;

    public ChildGuardianRelationship.Type getType() {
        return type;
    }

    public void setType(ChildGuardianRelationship.Type type) {
        this.type = type;
    }

    public Boolean getPickup() {
        return pickup;
    }

    public void setPickup(Boolean pickup) {
        this.pickup = pickup;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "RelationshipMetadata{" +
                "type=" + type +
                '}';
    }

}
