package fraglab.registry.school;

import java.io.Serializable;

public class ChildGroupStatistics implements Serializable {

    private String childGroupId;
    private Integer boysNumber;
    private Integer girlsNumber;
    private Integer preSchoolLevelANumber;
    private Integer preSchoolLevelBNumber;

    public ChildGroupStatistics() {
    }

    public ChildGroupStatistics(String childGroupId, Integer boysNumber, Integer girlsNumber, Integer preSchoolLevelANumber, Integer preSchoolLevelBNumber) {
        this.childGroupId = childGroupId;
        this.boysNumber = boysNumber;
        this.girlsNumber = girlsNumber;
        this.preSchoolLevelANumber = preSchoolLevelANumber;
        this.preSchoolLevelBNumber = preSchoolLevelBNumber;
    }

    public String getChildGroupId() {
        return childGroupId;
    }

    public void setChildGroupId(String childGroupId) {
        this.childGroupId = childGroupId;
    }

    public Integer getBoysNumber() {
        return boysNumber;
    }

    public void setBoysNumber(Integer boysNumber) {
        this.boysNumber = boysNumber;
    }

    public Integer getGirlsNumber() {
        return girlsNumber;
    }

    public void setGirlsNumber(Integer girlsNumber) {
        this.girlsNumber = girlsNumber;
    }

    public Integer getPreSchoolLevelANumber() {
        return preSchoolLevelANumber;
    }

    public void setPreSchoolLevelANumber(Integer preSchoolLevelANumber) {
        this.preSchoolLevelANumber = preSchoolLevelANumber;
    }

    public Integer getPreSchoolLevelBNumber() {
        return preSchoolLevelBNumber;
    }

    public void setPreSchoolLevelBNumber(Integer preSchoolLevelBNumber) {
        this.preSchoolLevelBNumber = preSchoolLevelBNumber;
    }

    @Override
    public String toString() {
        return "ChildGroupStatistics{" +
                "boysNumber='" + boysNumber + '\'' +
                ", girlsNumber='" + girlsNumber + '\'' +
                ", preSchoolLevelANumber='" + preSchoolLevelANumber + '\'' +
                ", preSchoolLevelBNumber='" + preSchoolLevelBNumber + '\'' +
                '}';
    }

}
