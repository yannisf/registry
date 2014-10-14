package fraglab.school.typeahead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAheadServiceImpl implements TypeAheadService {

    @Autowired
    TypeAheadDao typeAheadDao;

    @Override
    public List<String> findMatchingFirstNames(String startsWith) {
        return typeAheadDao.findMatchingFirstNames(startsWith);
    }

    @Override
    public List<String> findMatchingLastNames(String startsWith) {
        return typeAheadDao.findMatchingLastNames(startsWith);
    }

    @Override
    public List<String> findMatchingProfessions(String startsWith) {
        return typeAheadDao.findMatchingProfessions(startsWith);
    }

    @Override
    public List<String> findMatchingNationalities(String startsWith) {
        return typeAheadDao.findMatchingNationalities(startsWith);
    }

}
