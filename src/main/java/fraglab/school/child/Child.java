package fraglab.school.child;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.school.Person;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("CHILD")
public class Child extends Person {

    private String callName;

    @Enumerated(EnumType.STRING)
    private PreSchoolLevel level;

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