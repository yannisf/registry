package fraglab.school.relationship;

import fraglab.school.guardian.Guardian;

import java.io.Serializable;

public class GuardianRelationshipDto implements Serializable, Comparable<GuardianRelationshipDto> {

    private Guardian guardian;
    private ChildGuardianRelationship relationship;

    public GuardianRelationshipDto() {
    }

    public GuardianRelationshipDto(Guardian guardian, ChildGuardianRelationship relationship) {
        this.guardian = guardian;
        this.relationship = relationship;
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

    @Override
    public int compareTo(GuardianRelationshipDto other) {
        return relationship.getRelationshipMetadata().getType().compareTo(other.getRelationship().getRelationshipMetadata().getType());
    }
}
