package fraglab.registry.child;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.Person;
import fraglab.registry.school.SchoolClassYearAggregation;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CHILD")
public class Child extends Person {

    private String callName;

    @Enumerated(EnumType.STRING)
    private PreSchoolLevel level;

    @ManyToOne()
    private SchoolClassYearAggregation yearClass;

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
    public String getName() {
        return getCallName() != null ? getCallName() : getFirstName();
    }

    @JsonIgnore
    public SchoolClassYearAggregation getYearClass() {
        return yearClass;
    }

    public void setYearClass(SchoolClassYearAggregation yearClass) {
        this.yearClass = yearClass;
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