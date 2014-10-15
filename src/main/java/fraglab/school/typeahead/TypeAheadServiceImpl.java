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
        return typeAheadDao.findMatchingFirstNames(startsWith.toLowerCase());
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

    @Override
    public List<String> findMatchingStreetNames(String startsWith) {
        return typeAheadDao.findMatchingStreetNames(startsWith);
    }

    @Override
    public List<String> findMatchingNeighbourhoods(String startsWith) {
        return typeAheadDao.findMatchingNeighbourhoods(startsWith);
    }

    @Override
    public List<String> findMatchingCities(String startsWith) {
        return typeAheadDao.findMatchingCities(startsWith);
    }

}
