package fraglab.registry.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeElement implements Serializable {

    private String id;
    private String name;
    private String parentId;
    private Type type;
    private Integer members;
    private List<TreeElement> children;

    public TreeElement() {
    }

    public TreeElement(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TreeElement(String id, String name, String parentId) {
        this(id, name);
        this.parentId = parentId;
    }

    public TreeElement(String id, String name, String parentId, Integer members) {
        this(id, name, parentId);
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public List<TreeElement> getChildren() {
        return children;
    }

    public void setChildren(List<TreeElement> children) {
        this.children = children;
    }

    public void addChild(TreeElement child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "TreeElement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    public static enum Type {
        SCHOOL, CLASSROOM, TERM;
    }
}
