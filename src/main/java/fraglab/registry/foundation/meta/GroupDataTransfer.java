package fraglab.registry.foundation.meta;

import java.io.Serializable;

public class GroupDataTransfer implements Serializable {

    private String id;
    private String school;
    private String classroom;
    private String term;
    private Integer members;

    public GroupDataTransfer() { }

    public GroupDataTransfer(String id, String school, String classroom, String term, Integer members) {
        this.id = id;
        this.school = school;
        this.classroom = classroom;
        this.term = term;
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

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

}
