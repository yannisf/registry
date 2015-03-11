package fraglab.registry.foundation.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeElement implements Serializable {

    private String id;
    private String name;
    private String parentId;
    private Type type;
    private Integer members;
    private List<TreeElement> childNodes;

    public TreeElement() { }

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

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
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

    public List<TreeElement> getChildNodes() {
        return childNodes;
    }

    public void addChild(TreeElement child) {
        if (this.childNodes == null) {
            this.childNodes = new ArrayList<>();
        }
        this.childNodes.add(child);
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
        SCHOOL, DEPARTMENT, GROUP;
    }

}
