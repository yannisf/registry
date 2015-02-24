package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public List<TreeElement> fetchSchoolTreeElements() {
        return schoolDao.fetchSchoolTreeElements();
    }

    @Override
    public SchoolData fetchSchoolData(String childGroupId) {
        return schoolDao.fetchSchoolData(childGroupId);
    }

    @Override
    public ChildGroupStatistics fetchChildGroupStatistics(String childGroupId) {
        return schoolDao.fetchClassroomStatistics(childGroupId);
    }

    @Override
    public void update(School school) {
        schoolDao.update(school);
    }

    @Override
    public void createOrUpdateTerm(Term term) {
        schoolDao.createOrUpdateTerm(term);
    }

    @Override
    public void createOrUpdateClassroom(Classroom classroom) {
        schoolDao.createOrUpdateClassroom(classroom);
    }

    @Override
    public void createOrUpdateGroup(Group group) {
        schoolDao.createOrUpdateGroup(group);
    }

}
