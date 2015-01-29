package fraglab.registry.school;

import java.util.List;

public interface SchoolService {

    List<School> fetchSchools();

    SchoolData fetchSchoolData(String yearClassId);

    List<SchoolTreeElement> fetchSchoolTreeElements();

    List<SchoolClass> fetchClassesForSchool(String id);

    void updateSchool(School school);

    void updateClassForSchool(String id, SchoolClass schoolClass);

    List<SchoolYear> fetchYears();

    void updateYear(SchoolYear year);
}
