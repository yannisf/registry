package fraglab.registry.child;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.Person;
import fraglab.registry.school.ChildGroup;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CHILD")
public class Child extends Person {

    private String callName;

    @Column(name = "PRESCHOOL_LEVEL")
    @Enumerated(EnumType.STRING)
    private PreSchoolLevel level;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_GROUP_ID", updatable = false, insertable = false)
    private ChildGroup childGroup;

    @Column(name = "CHILD_GROUP_ID", updatable = true, insertable = true)
    private String childGroupId;

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getInformalFullName() {
        return StringUtils.stripToEmpty(getName() + StringUtils.SPACE + getLastName());
    }

    public PreSchoolLevel getLevel() {
        return level;
    }

    public void setLevel(PreSchoolLevel level) {
        this.level = level;
    }

    @JsonIgnore
    public String getName() {
        return getCallName() != null ? getCallName() : getFirstName();
    }

    public String getReportName() {
        return getName() + " " + getLastName();
    }

    public String getChildGroupId() {
        return childGroupId;
    }

    public void setChildGroupId(String childGroupId) {
        this.childGroupId = childGroupId;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", callName='" + getCallName() + '\'' +
                '}';
    }

    public enum PreSchoolLevel {
        PRE_SCHOOL_LEVEL_A,
        PRE_SCHOOL_LEVEL_B
    }

}