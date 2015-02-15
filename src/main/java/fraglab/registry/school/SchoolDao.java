package fraglab.registry.school;

import java.util.List;

public interface SchoolDao {

    SchoolData fetchSchoolData(String childGroupId);

    void init();

    List<TreeElement> fetchSchoolTreeElements();

    List<School> fetchSchools();

    List<Classroom> fetchClassroomsForSchool(String id);

    void updateSchool(School school);

    void updateClassroomForSchool(String id, Classroom classroom);

    List<Term> fetchTerms();

    void updateTerm(Term year);

}
