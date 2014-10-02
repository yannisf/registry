package fraglab.school.relationship;

import java.io.Serializable;

public class RelationshipDto implements Serializable {

    private Long guardianId;

    private String guardianFirstName;

    private String guardianLastName;

    private ChildGuardianRelationship.Type relationshipType;

    public RelationshipDto() {
    }

    public RelationshipDto(Long guardianId, String guardianFirstName, String guardianLastName, ChildGuardianRelationship.Type relationshipType) {
        this.guardianId = guardianId;
        this.guardianFirstName = guardianFirstName;
        this.guardianLastName = guardianLastName;
        this.relationshipType = relationshipType;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public ChildGuardianRelationship.Type getRelationshipType() {
        return relationshipType;
    }

}
