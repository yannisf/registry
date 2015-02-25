package fraglab.registry.foundation;

import fraglab.registry.common.BaseEntity;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Term extends BaseEntity {

    private String name;

    public Term() { }

    public Term(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
