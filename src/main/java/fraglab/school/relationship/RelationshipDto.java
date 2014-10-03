package fraglab.school.relationship;

import fraglab.school.child.Child;
import fraglab.school.guardian.Guardian;

import java.io.Serializable;

public class RelationshipDto implements Serializable {

    private Child child;
    private Guardian guardian;
    private ChildGuardianRelationship relationship;

    public RelationshipDto() {
    }

    public RelationshipDto(Child child, Guardian guardian, ChildGuardianRelationship relationship) {
        this.child = child;
        this.guardian = guardian;
        this.relationship = relationship;
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

    public ChildGuardianRelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ChildGuardianRelationship relationship) {
        this.relationship = relationship;
    }

}
