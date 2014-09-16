
package fraglab.school.affinity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "C_G_AFFINITY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CHILD_ID", "GROWNUP_ID"})
})
public class ChildGrownUpAffinity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CHILD_ID")
    private Long childId;

    @Column(name = "GROWNUP_ID")
    private Long grownupId;

    @Embedded
    private AffinityMetadata affinityMetadata;

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

    public Long getGrownupId() {
        return grownupId;
    }

    public void setGrownupId(Long grownupId) {
        this.grownupId = grownupId;
    }

    public AffinityMetadata getAffinityMetadata() {
        return affinityMetadata;
    }

    public void setAffinityMetadata(AffinityMetadata affinityMetadata) {
        this.affinityMetadata = affinityMetadata;
    }

    public enum Type {
        PARENT,
        GRANDPARENT,
        SIBLING,
        OTHER;
    }

}
