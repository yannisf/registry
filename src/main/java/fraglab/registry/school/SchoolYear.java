package fraglab.registry.school;

import fraglab.registry.BaseEntity;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class SchoolYear extends BaseEntity {

    private String label;

    public SchoolYear() {
        this.id = UUID.randomUUID().toString();
    }

    public SchoolYear(String label) {
        this();
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
