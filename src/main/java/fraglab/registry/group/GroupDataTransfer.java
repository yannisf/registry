package fraglab.registry.group;

import java.io.Serializable;

public class GroupDataTransfer implements Serializable {

    private String id;
    private String school;
    private String department;
    private String group;
    private Integer members;

    public GroupDataTransfer() { }

    public GroupDataTransfer(String id, String school, String department, String group, Integer members) {
        this.id = id;
        this.school = school;
        this.department = department;
        this.group = group;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

}
