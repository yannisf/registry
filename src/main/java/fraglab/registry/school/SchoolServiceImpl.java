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
    public List<TreeElement> fetchSchoolTreeElements() {
        return schoolDao.fetchSchoolTreeElements();
    }

    @Override
    public List<Classroom> fetchClassroomsForSchool(String id) {
        return schoolDao.fetchClassroomsForSchool(id);
    }

    @Override
    public void updateSchool(School school) {
        schoolDao.updateSchool(school);
    }

    @Override
    public void updateClassroomForSchool(String id, Classroom classroom) {
        schoolDao.updateClassroomForSchool(id, classroom);
    }

    @Override
    public List<Term> fetchTerms() {
        return schoolDao.fetchTerms();
    }

    @Override
    public void updateTerm(Term year) {
        schoolDao.updateTerm(year);
    }

}
