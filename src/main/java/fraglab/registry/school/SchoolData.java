package fraglab.registry.school;

import java.io.Serializable;

public class SchoolData implements Serializable {

    private String school;
    private String clazz;
    private String year;

    public SchoolData() {
    }

    public SchoolData(String school, String clazz, String year) {
        this.school = school;
        this.clazz = clazz;
        this.year = year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
