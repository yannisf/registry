
package fraglab.school.relationship;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CHILD_GUARDIAN", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CHILD_ID", "GUARDIAN_ID"})
})
public class ChildGuardianRelationship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CHILD_ID")
    private Long childId;

    @Column(name = "GUARDIAN_ID")
    private Long guardianId;

    @Embedded
    private RelationshipMetadata relationshipMetadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        PARENT,
        GRANDPARENT,
        SIBLING,
        OTHER;
    }

}
