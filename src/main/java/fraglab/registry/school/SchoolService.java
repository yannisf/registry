package fraglab.registry.school;

import java.util.List;

public interface SchoolService {

    SchoolData fetchSchoolData(String yearClassId);

    List<SchoolTreeElement> fetchSchoolTreeElements();
}
