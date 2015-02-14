package fraglab.registry.school;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Classroom extends BaseEntity {

    private String name;

    @ManyToOne(optional = false)
    private School school;

    public Classroom() {
        this.id = UUID.randomUUID().toString();
    }

    public Classroom(String name) {
        this();
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
