package fraglab.registry.school;

import java.util.List;

public interface SchoolService {

    List<School> fetchSchools();

    SchoolData fetchSchoolData(String yearClassId);

    List<TreeElement> fetchSchoolTreeElements();

    List<Classroom> fetchClassroomsForSchool(String id);

    void updateSchool(School school);

    void updateClassroomForSchool(String id, Classroom classroom);

    List<Term> fetchTerms();

    void updateTerm(Term year);
}
