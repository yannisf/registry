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
        RelationshipMetadata otherRelationshipMetadata = other.getRelationship().getRelationshipMetadata();
        RelationshipMetadata thisRelationshipMetadata = relationship.getRelationshipMetadata();
        if (otherRelationshipMetadata == null || otherRelationshipMetadata.getType() == null) {
            return -1;
        } else if (thisRelationshipMetadata == null || thisRelationshipMetadata.getType() == null) {
            return 1;
        } else {
            return thisRelationshipMetadata.getType().compareTo(otherRelationshipMetadata.getType());
        }
    }
}
