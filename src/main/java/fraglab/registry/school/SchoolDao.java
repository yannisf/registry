package fraglab.registry.school;

import java.util.List;

public interface SchoolDao {

    SchoolData fetchSchoolData(String yearClassId);

    void execute();

    List<SchoolTreeElement> fetchSchoolTreeElements();

    List<School> fetchSchools();

    List<SchoolClass> fetchClassesForSchool(String id);

    void updateSchool(School school);

    void updateClassForSchool(String id, SchoolClass schoolClass);

    List<SchoolYear> fetchYears();

    void updateYear(SchoolYear year);

}
