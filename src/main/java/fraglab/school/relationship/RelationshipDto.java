package fraglab.school.relationship;

import fraglab.school.guardian.Guardian;

import java.io.Serializable;

public class RelationshipDto implements Serializable {

    private String childId;
    private Guardian guardian;
    private RelationshipMetadata relationshipMetadata;

    public RelationshipDto() {
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public RelationshipMetadata getRelationshipMetadata() {
        return relationshipMetadata;
    }

    public void setRelationshipMetadata(RelationshipMetadata relationshipMetadata) {
        this.relationshipMetadata = relationshipMetadata;
    }

    @Override
    public String toString() {
        return "RelationshipDto{" +
                "childId='" + childId + '\'' +
                ", guardian=" + guardian +
                ", relationshipMetadata=" + relationshipMetadata +
                '}';
    }
}
