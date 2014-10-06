
package fraglab.school.relationship;

import fraglab.school.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "CHILD_GUARDIAN", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CHILD_ID", "GUARDIAN_ID"})
})
public class ChildGuardianRelationship extends BaseEntity {

    @Column(name = "CHILD_ID")
    private Long childId;

    @Column(name = "GUARDIAN_ID")
    private Long guardianId;

    @Embedded
    private RelationshipMetadata relationshipMetadata;

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
    }

    public RelationshipMetadata getRelationshipMetadata() {
        return relationshipMetadata;
    }

    public void setRelationshipMetadata(RelationshipMetadata relationshipMetadata) {
        this.relationshipMetadata = relationshipMetadata;
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
