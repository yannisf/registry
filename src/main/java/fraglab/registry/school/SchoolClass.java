package fraglab.registry.school;

import fraglab.registry.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class SchoolClass extends BaseEntity {

    private String label;

    @ManyToOne(optional = false)
    private School school;

    public SchoolClass() {
        this.id = UUID.randomUUID().toString();
    }

    public SchoolClass(String label) {
        this();
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
