package fraglab.registry.school;

import java.util.List;

public interface SchoolService {

    List<TreeElement> fetchSchoolTreeElements();
    
    SchoolData fetchSchoolData(String childGroupId);

    ChildGroupStatistics fetchChildGroupStatistics(String childGroupId);

    void update(School school);

    void createOrUpdateTerm(Term term);

    void createOrUpdateClassroom(Classroom classroom);

    void createOrUpdateGroup(Group group);
}
