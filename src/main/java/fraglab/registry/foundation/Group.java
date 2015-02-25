package fraglab.registry.foundation;

import fraglab.registry.child.Child;
import fraglab.registry.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CGROUP")
public class Group extends BaseEntity {

    @ManyToOne(optional = false)
    private Classroom classroom;

    @ManyToOne(optional = false)
    private Term term;

    @OneToMany(mappedBy = "group")
    private List<Child> children;

    private Integer members;

    public Group() {
        this.id = UUID.randomUUID().toString();
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

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
