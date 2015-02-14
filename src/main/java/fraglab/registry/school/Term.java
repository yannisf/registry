package fraglab.registry.school;

import fraglab.registry.common.BaseEntity;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Term extends BaseEntity {

    private String name;

    public Term() {
        this.id = UUID.randomUUID().toString();
    }

    public Term(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}