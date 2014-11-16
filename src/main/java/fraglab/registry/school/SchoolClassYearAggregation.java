package fraglab.registry.school;

import fraglab.registry.BaseEntity;
import fraglab.registry.child.Child;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "YEAR_CLASS")
public class SchoolClassYearAggregation extends BaseEntity {

    @ManyToOne(optional = false)
    private SchoolClass clazz;

    @ManyToOne(optional = false)
    private SchoolYear year;

    @OneToMany(mappedBy = "yearClass")
    private List<Child> children;

    public SchoolClassYearAggregation() {
        this.id = UUID.randomUUID().toString();
    }

    public SchoolClass getClazz() {
        return clazz;
    }

    public void setClazz(SchoolClass clazz) {
        this.clazz = clazz;
    }

    public SchoolYear getYear() {
        return year;
    }

    public void setYear(SchoolYear year) {
        this.year = year;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

}
