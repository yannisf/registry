package fraglab.registry.foundation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.BaseEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department extends BaseEntity {

    private String name;

    @ManyToOne(optional = false)
    private School school;

    @OneToMany(mappedBy = "department")
    @OrderBy(value = "name desc")
    private List<Group> groups;

    @Formula(value = "(select count(*) from CGROUP g where g.department_id = id)")
    private Long numberOfGroups;

    public Department() { }

    public Department(String name) {
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

    @JsonIgnore
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }
        this.groups.add(group);
        group.setDepartment(this);
    }

    public Long getNumberOfGroups() {
        return numberOfGroups;
    }
    
}
