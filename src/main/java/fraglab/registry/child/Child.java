package fraglab.registry.child;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.Person;
import fraglab.registry.school.ChildGroup;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("CHILD")
public class Child extends Person {

    private String callName;

    @Column(name = "PRESCHOOL_LEVEL")
    @Enumerated(EnumType.STRING)
    private PreSchoolLevel level;

    @OneToMany
    private List<Relationship> relationships;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private ChildGroup childGroup;

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public PreSchoolLevel getLevel() {
        return level;
    }

    public void setLevel(PreSchoolLevel level) {
        this.level = level;
    }

    @JsonIgnore
    public ChildGroup getChildGroup() {
        return childGroup;
    }

    public void setChildGroup(ChildGroup childGroup) {
        this.childGroup = childGroup;
    }

    @JsonIgnore
    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    @JsonIgnore
    public String getSimpleName() {
        return getCallName() != null ? getCallName() : getFirstName();
    }

    @JsonIgnore
    public String getReportName() {
        return getSimpleName() + StringUtils.SPACE + getLastName();
    }

    @JsonIgnore
    public String getInformalFullName() {
        return StringUtils.stripToEmpty(getSimpleName() + StringUtils.SPACE + getLastName());
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