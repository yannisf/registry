package fraglab.registry.foundation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.child.Child;
import fraglab.registry.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CGROUP")
public class Group extends BaseEntity {

    private String name;

    @ManyToOne(optional = false)
    private Department department;

    @OneToMany(mappedBy = "group")
    private List<Child> children;

    private Integer members;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonIgnore
    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public void addChild(Child child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
        child.setGroup(this);
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

}
