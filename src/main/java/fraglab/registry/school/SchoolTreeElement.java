package fraglab.registry.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchoolTreeElement implements Serializable {

    private String id;
    private String label;
    private String parentId;
    private Type type;
    private List<SchoolTreeElement> children;

    public SchoolTreeElement() {
    }

    public SchoolTreeElement(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public SchoolTreeElement(String id, String label, String parentId) {
        this(id, label);
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<SchoolTreeElement> getChildren() {
        return children;
    }

    public void setChildren(List<SchoolTreeElement> children) {
        this.children = children;
    }

    public void addChild(SchoolTreeElement child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "SchoolTreeElement{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    public static enum Type {
        SCHOOL, CLASS, YEAR;
    }
}
