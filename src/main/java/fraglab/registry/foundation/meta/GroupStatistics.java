package fraglab.registry.foundation.meta;

import java.io.Serializable;

public class GroupStatistics implements Serializable {

    private String groupId;
    private Integer boysNum;
    private Integer girlsNum;
    private Integer levelaNum;
    private Integer levelbNum;

    public GroupStatistics() { }

    public GroupStatistics(String groupId, Integer boysNum, Integer girlsNum, Integer levelaNum, Integer levelbNum) {
        this.groupId = groupId;
        this.boysNum = boysNum;
        this.girlsNum = girlsNum;
        this.levelaNum = levelaNum;
        this.levelbNum = levelbNum;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getBoysNum() {
        return boysNum;
    }

    public void setBoysNum(Integer boysNum) {
        this.boysNum = boysNum;
    }

    public Integer getGirlsNum() {
        return girlsNum;
    }

    public void setGirlsNum(Integer girlsNum) {
        this.girlsNum = girlsNum;
    }

    public Integer getLevelaNum() {
        return levelaNum;
    }

    public void setLevelaNum(Integer levelaNum) {
        this.levelaNum = levelaNum;
    }

    public Integer getLevelbNum() {
        return levelbNum;
    }

    public void setLevelbNum(Integer levelbNum) {
        this.levelbNum = levelbNum;
    }

    @Override
    public String toString() {
        return "ChildGroupStatistics{" +
                "boysNum='" + boysNum + '\'' +
                ", girlsNum='" + girlsNum + '\'' +
                ", levelbNum='" + levelbNum + '\'' +
                ", levelaNum='" + levelaNum + '\'' +
                '}';
    }

}
