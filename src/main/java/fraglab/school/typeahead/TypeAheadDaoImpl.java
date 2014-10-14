package fraglab.school.typeahead;

import fraglab.GenericDaoImpl;
import fraglab.school.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TypeAheadDaoImpl extends GenericDaoImpl<Person, Long> implements TypeAheadDao {

    @Override
    public List<String> findMatchingFirstNames(String startsWith) {
        Query query = entityManager.createQuery("select distinct p.firstName from Person p");// where p.firstName like :startsWith");
//        query.setParameter("startsWith", startsWith + "%");
        return query.getResultList();
    }

    @Override
    public List<String> findMatchingLastNames(String startsWith) {
        Query query = entityManager.createQuery("");
        query.setParameter("", "");
        return query.getResultList();
    }

    @Override
    public List<String> findMatchingProfessions(String startsWith) {
        Query query = entityManager.createQuery("");
        query.setParameter("", "");
        return query.getResultList();

    }

    @Override
    public List<String> findMatchingNationalities(String startsWith) {
        Query query = entityManager.createQuery("");
        query.setParameter("", "");
        return query.getResultList();
    }

}
