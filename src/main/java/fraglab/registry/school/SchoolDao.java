package fraglab.registry.school;

import java.util.List;

public interface SchoolDao {

    SchoolData fetchSchoolData(String yearClassId);

    void execute();

    List<SchoolTreeElement> fetchSchoolTreeElements();

    List<School> fetchSchools();

    List<SchoolClass> fetchClassesForSchool(String id);
}
