package fraglab.school.affinity;

import java.io.Serializable;

public class AffinityDto implements Serializable {

    private Long grownupId;

    private String grownupFirstName;

    private String grownupLastName;

    private ChildGrownUpAffinity.Type affinityType;

    public AffinityDto() {
    }

    public AffinityDto(Long grownupId, String grownupFirstName, String grownupLastName, ChildGrownUpAffinity.Type affinityType) {
        this.grownupId = grownupId;
        this.grownupFirstName = grownupFirstName;
        this.grownupLastName = grownupLastName;
        this.affinityType = affinityType;
    }

    public Long getGrownupId() {
        return grownupId;
    }

    public String getGrownupFirstName() {
        return grownupFirstName;
    }

    public String getGrownupLastName() {
        return grownupLastName;
    }

    public ChildGrownUpAffinity.Type getAffinityType() {
        return affinityType;
    }

}
