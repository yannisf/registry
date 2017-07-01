package fraglab.registry.relationship;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class RelationshipMetadata implements Serializable {

    @Enumerated(EnumType.STRING)
    private RelationshipType type;

    private String notes;

    public RelationshipMetadata() {
    }

    public RelationshipMetadata(RelationshipType type) {
        this.type = type;
    }

    public RelationshipType getType() {
        return type;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "RelationshipMetadata{type=" + type + '}';
    }

}
