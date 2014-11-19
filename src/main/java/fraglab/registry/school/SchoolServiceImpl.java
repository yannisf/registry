package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public SchoolData fetchSchoolData(String yearClassId) {
        return schoolDao.fetchSchoolData(yearClassId);
    }

    @Override
    public List<SchoolTreeElement> fetchSchoolTreeElements() {
        return schoolDao.fetchSchoolTreeElements();
    }
}
