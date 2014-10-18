package fraglab.school.typeahead;

import fraglab.GenericDaoImpl;
import fraglab.school.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TypeAheadDaoImpl extends GenericDaoImpl<Person, Long> implements TypeAheadDao {

    public static final Integer MAX_RESULTS = 5;

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingFirstNames(String startsWith) {
        Query query = entityManager.createQuery("select distinct p.firstName from Person p " +
                "where lower(p.firstName) like :startsWith order by p.firstName");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingLastNames(String startsWith) {
        Query query = entityManager.createQuery("select distinct p.lastName from Person p " +
                "where lower(p.lastName) like :startsWith order by p.lastName");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingProfessions(String startsWith) {
        Query query = entityManager.createQuery("select distinct g.profession from Guardian g " +
                "where lower(g.profession) like :startsWith order by g.profession");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingNationalities(String startsWith) {
        Query query = entityManager.createQuery("select distinct p.nationality from Person p " +
                "where lower(p.nationality) like :startsWith order by p.nationality");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingStreetNames(String startsWith) {
        Query query = entityManager.createQuery("select distinct a.streetName from Address a " +
                "where lower(a.streetName) like :startsWith order by a.streetName");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingNeighbourhoods(String startsWith) {
        Query query = entityManager.createQuery("select distinct a.neighbourhood from Address a " +
                "where lower(a.neighbourhood) like :startsWith order by a.neighbourhood");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<String> findMatchingCities(String startsWith) {
        Query query = entityManager.createQuery("select distinct a.city from Address a " +
                "where lower(a.city) like :startsWith order by a.city");
        query.setParameter("startsWith", startsWith.toLowerCase() + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

}
