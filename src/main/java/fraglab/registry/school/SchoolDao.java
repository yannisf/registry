package fraglab.registry.school;

import java.util.List;

public interface SchoolDao {

    void init();
    
    SchoolData fetchSchoolData(String childGroupId);

    List<TreeElement> fetchSchoolTreeElements();

}
