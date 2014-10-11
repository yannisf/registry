package fraglab.school;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UuidBaseEntity extends BaseEntity {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
