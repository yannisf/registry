package fraglab.registry.school;

import fraglab.registry.child.Child;
import fraglab.registry.common.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CHILD_GROUP")
public class ChildGroup extends BaseEntity {

    @ManyToOne(optional = false)
    private Classroom classroom;

    @ManyToOne(optional = false)
    private Term term;

    @OneToMany(mappedBy = "childGroup")
    private List<Child> children;

    private Integer members;

    public ChildGroup() {
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

}
