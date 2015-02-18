package fraglab.registry.school;

import java.util.List;

public interface SchoolService {

    void init();

    List<TreeElement> fetchSchoolTreeElements();
    
    SchoolData fetchSchoolData(String childGroupId);

    ChildGroupStatistics fetchChildGroupStatistics(String childGroupId);

}
