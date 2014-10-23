
package fraglab.school.relationship;

import fraglab.school.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CHILD_GUARDIAN", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CHILD_ID", "GUARDIAN_ID"})
})
public class ChildGuardianRelationship extends BaseEntity {

    @Column(name = "CHILD_ID")
    private String childId;

    @Column(name = "GUARDIAN_ID")
    private String guardianId;
    @Embedded
    private RelationshipMetadata relationshipMetadata;

    public ChildGuardianRelationship() {
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

    public RelationshipMetadata getRelationshipMetadata() {
        return relationshipMetadata;
    }

    public void setRelationshipMetadata(RelationshipMetadata relationshipMetadata) {
        this.relationshipMetadata = relationshipMetadata;
    }

    @Override
    public String toString() {
        return "ChildGuardianRelationship{" +
                "childId=" + childId +
                ", guardianId=" + guardianId +
                ", relationshipMetadata=" + relationshipMetadata +
                '}';
    }

    public enum Type {
        FATHER,
        MOTHER,
        GRANDFATHER,
        GRANDMOTHER,
        BROTHER,
        SISTER,
        UNCLE,
        AUNT,
        GODFATHER,
        GODMOTHER,
        OTHER;
    }

}
