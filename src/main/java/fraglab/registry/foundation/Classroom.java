package fraglab.registry.foundation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Classroom extends BaseEntity {

    private String name;

    @ManyToOne(optional = false)
    private School school;

    public Classroom() { }

    public Classroom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    
}
