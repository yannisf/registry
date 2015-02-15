package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public void init() {
        schoolDao.init();
    }

    @Override
    public List<TreeElement> fetchSchoolTreeElements() {
        return schoolDao.fetchSchoolTreeElements();
    }

    @Override
    public SchoolData fetchSchoolData(String yearClassId) {
        return schoolDao.fetchSchoolData(yearClassId);
    }

}
