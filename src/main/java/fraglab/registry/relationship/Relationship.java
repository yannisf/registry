
package fraglab.registry.relationship;

import fraglab.registry.child.Child;
import fraglab.registry.common.BaseEntity;
import fraglab.registry.guardian.Guardian;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CHILD_GUARDIAN", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CHILD_ID", "GUARDIAN_ID"})
})
public class Relationship extends BaseEntity implements Comparable<Relationship> {

    @Column(name = "CHILD_ID")
    private String childId;

    @Column(name = "GUARDIAN_ID")
    private String guardianId;

    @Embedded
    private RelationshipMetadata metadata;

    @Transient
    private Child child;

    @Transient
    private Guardian guardian;

    public Relationship() {
        this.id = UUID.randomUUID().toString();
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    public RelationshipMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RelationshipMetadata metadata) {
        this.metadata = metadata;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    @Override
    public int compareTo(Relationship o) {
        if (metadata != null && metadata.getType() != null && o.metadata != null && o.metadata.getType() != null) {
            return metadata.getType().compareTo(o.metadata.getType());
        } else if (o.metadata == null || o.metadata.getType() == null) {
            return 1;
        } else if (metadata == null || metadata.getType() == null) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "childId=" + childId +
                ", guardianId=" + guardianId +
                ", metadata=" + metadata +
                '}';
    }

    public enum Type {
        FATHER, MOTHER,
        GRANDFATHER, GRANDMOTHER,
        BROTHER, SISTER,
        UNCLE, AUNT,
        GODFATHER, GODMOTHER,
        OTHER
    }

    @Embeddable
    public static class RelationshipMetadata implements Serializable {

        @Enumerated(EnumType.STRING)
        private Relationship.Type type;

        private String notes;

        private boolean pickup;

        public Relationship.Type getType() {
            return type;
        }

        public void setType(Relationship.Type type) {
            this.type = type;
        }

        public boolean getPickup() {
            return pickup;
        }

        public void setPickup(boolean pickup) {
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
            return "RelationshipMetadata{type=" + type + '}';
        }

    }

}
