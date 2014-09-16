package fraglab.school.affinity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class AffinityMetadata implements Serializable {

    @Enumerated(EnumType.STRING)
    private ChildGrownUpAffinity.Type type;

    private Boolean pickup;

    public ChildGrownUpAffinity.Type getType() {
        return type;
    }

    public void setType(ChildGrownUpAffinity.Type type) {
        this.type = type;
    }

    public Boolean getPickup() {
        return pickup;
    }

    public void setPickup(Boolean pickup) {
        this.pickup = pickup;
    }

}
