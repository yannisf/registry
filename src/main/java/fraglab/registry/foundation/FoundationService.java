package fraglab.registry.foundation;

import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;
import fraglab.web.NotFoundException;

import java.util.List;

public interface FoundationService {

    List<TreeElement> fetchSchoolTreeElements();
    
    GroupDataTransfer fetchSchoolData(String childGroupId);

    List<Child> fetchChildrenForGroup(String groupId);

    List<String> fetchChildrenIdsForGroup(String groupId);

    GroupStatistics fetchChildGroupStatistics(String childGroupId);

    void createOrUpdateSchool(School school);

    void createOrUpdateClassroom(Classroom classroom);

    void createOrUpdateGroup(Group group);

    List<School> fetchSchools();

    School fetchSchool(String id) throws NotFoundException;

    List<Classroom> fetchClassroomsForSchool(String schoolId);

    void deleteSchool(String id);

    void createOrUpdateClassroomForSchool(String schoolId, Classroom classroom);
}
