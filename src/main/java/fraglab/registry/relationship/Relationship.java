
package fraglab.registry.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.child.Child;
import fraglab.registry.common.BaseEntity;
import fraglab.registry.guardian.Guardian;

import javax.persistence.*;

@Entity
public class Relationship extends BaseEntity implements Comparable<Relationship> {

    @Embedded
    private RelationshipMetadata metadata;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Child child;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private Guardian guardian;

    public Relationship() { }

    public RelationshipMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RelationshipMetadata metadata) {
        this.metadata = metadata;
    }

    @JsonIgnore
    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

//    @JsonIgnore
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
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "child=" + child +
                ", guardian=" + guardian +
                ", metadata=" + metadata +
                '}';
    }

}
