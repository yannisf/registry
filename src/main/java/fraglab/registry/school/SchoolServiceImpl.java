package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public List<School> fetchSchools() {
        return schoolDao.fetchSchools();
    }

    @Override
    public SchoolData fetchSchoolData(String yearClassId) {
        return schoolDao.fetchSchoolData(yearClassId);
    }

    @Override
    public List<SchoolTreeElement> fetchSchoolTreeElements() {
        return schoolDao.fetchSchoolTreeElements();
    }

    @Override
    public List<SchoolClass> fetchClassesForSchool(String id) {
        return schoolDao.fetchClassesForSchool(id);
    }

    @Override
    public void updateSchool(School school) {
        schoolDao.updateSchool(school);
    }

    @Override
    public void updateClassForSchool(String id, SchoolClass schoolClass) {
        schoolDao.updateClassForSchool(id, schoolClass);
    }

    @Override
    public List<SchoolYear> fetchYears() {
        return schoolDao.fetchYears();
    }

    @Override
    public void updateYear(SchoolYear year) {
        schoolDao.updateYear(year);
    }

}
