package fraglab.registry.foundation;

import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;

import java.util.List;

public interface FoundationService {

    List<TreeElement> fetchSchoolTreeElements();
    
    GroupDataTransfer fetchSchoolData(String childGroupId);

    List<Child> fetchChildrenForGroup(String groupId);

    GroupStatistics fetchChildGroupStatistics(String childGroupId);

    void createOrUpdateSchool(School school);

    void createOrUpdateClassroom(Classroom classroom);

    void createOrUpdateTerm(Term term);

    void createOrUpdateGroup(Group group);
}
