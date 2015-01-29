package fraglab.registry.school;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class School extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolClass> classes;

    public School() {
        this.id = UUID.randomUUID().toString();
    }

    public School(String name) {
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
    public List<SchoolClass> getClasses() {
        return classes;
    }

    public void setClasses(List<SchoolClass> classes) {
        this.classes = classes;
    }

    public void addClass(SchoolClass schoolClass) {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        schoolClass.setSchool(this);
        classes.add(schoolClass);
    }

}
