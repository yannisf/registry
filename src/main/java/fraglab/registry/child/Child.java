package fraglab.registry.child;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.common.Person;
import fraglab.registry.group.Group;
import fraglab.registry.relationship.Relationship;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CHILD")
public class Child extends Person {

    private String callName;

    private String privateNotes;

    @Column(name = "PRESCHOOL_LEVEL")
    @Enumerated(EnumType.STRING)
    private PreschoolLevel level;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private List<Relationship> relationships;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ChildPhoto photo;

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    public PreschoolLevel getLevel() {
        return level;
    }

    public void setLevel(PreschoolLevel level) {
        this.level = level;
    }

    @JsonIgnore
    public ChildPhoto getPhoto() {
        return photo;
    }

    public String getPhotoId() {
        return (photo != null) ? photo.getId() : null;
    }

    public void setPhoto(ChildPhoto photo) {
        this.photo = photo;
    }

    @JsonIgnore
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @JsonIgnore
    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public void addRelationship(Relationship relationship) {
        if (this.relationships == null) {
            this.relationships = new ArrayList<>();
        }
        relationships.add(relationship);
        relationship.setChild(this);
    }

    @JsonIgnore
    public String getSimpleName() {
        return getCallName() != null ? getCallName() : getFirstName();
    }

    @JsonIgnore
    public String getReportName() {
        return getSimpleName() + StringUtils.SPACE + getLastName();
    }

    @JsonIgnore
    public String getInformalFullName() {
        return StringUtils.stripToEmpty(getSimpleName() + StringUtils.SPACE + getLastName());
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", callName='" + getCallName() + '\'' +
                '}';
    }

    public enum PreschoolLevel {
        PRE_SCHOOL_LEVEL_A,
        PRE_SCHOOL_LEVEL_B
    }

}