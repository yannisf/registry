package fraglab.registry.school;

import fraglab.data.GenericDao;

import java.util.List;

public interface SchoolDao extends GenericDao<School, String> {

    SchoolData fetchSchoolData(String childGroupId);

    List<TreeElement> fetchSchoolTreeElements();

    ChildGroupStatistics fetchClassroomStatistics(String childGroupId);

    void createOrUpdateTerm(Term term);

    void createOrUpdateClassroom(Classroom classroom);

    void createOrUpdateGroup(Group group);
}
