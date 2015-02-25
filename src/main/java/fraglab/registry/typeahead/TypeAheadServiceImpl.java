package fraglab.registry.typeahead;

import fraglab.data.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeAheadServiceImpl implements TypeAheadService {

    public static final Integer MAX_RESULTS = 5;

    @Autowired
    GenericDao dao;

    @Override
    public List<String> findMatchingFirstNames(String startsWith) {
        String query = "select distinct p.firstName from Person p " +
                "where lower(p.firstName) like :startsWith order by p.firstName";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingLastNames(String startsWith) {
        String query = "select distinct p.lastName from Person p " +
                "where lower(p.lastName) like :startsWith order by p.lastName";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingProfessions(String startsWith) {
        String query = "select distinct g.profession from Guardian g " +
                "where lower(g.profession) like :startsWith order by g.profession";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingNationalities(String startsWith) {
        String query = "select distinct p.nationality from Person p " +
                "where lower(p.nationality) like :startsWith order by p.nationality";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingStreetNames(String startsWith) {
        String query = "select distinct a.streetName from Address a " +
                "where lower(a.streetName) like :startsWith order by a.streetName";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingNeighbourhoods(String startsWith) {
        String query = "select distinct a.neighbourhood from Address a " +
                "where lower(a.neighbourhood) like :startsWith order by a.neighbourhood";
        return findTypeAhead(query, startsWith);
    }

    @Override
    public List<String> findMatchingCities(String startsWith) {
        String query = "select distinct a.city from Address a " +
                "where lower(a.city) like :startsWith order by a.city";
        return findTypeAhead(query, startsWith);
    }

    private List<String> findTypeAhead(String query, String startsWith) {
        Map<String, Object> params = new HashMap<>();
        params.put("startsWith", startsWith.toLowerCase() + "%");
        return dao.findByQuery(query, params, MAX_RESULTS);
    }

}
