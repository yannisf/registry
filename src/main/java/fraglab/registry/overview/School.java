package fraglab.registry.overview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.BaseEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class School extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "school")
    private List<Department> departments;
    
    @Formula(value = "(select count(*) from department d where d.school_id = id)")
    private Long numberOfDepartments;

    public School() { }

    public School(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        department.setSchool(this);
        departments.add(department);
    }

    public Long getNumberOfDepartments() {
        return numberOfDepartments;
    }
    
}
