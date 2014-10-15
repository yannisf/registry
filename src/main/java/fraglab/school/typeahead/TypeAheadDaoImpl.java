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
    public List<String> findMatchingFirstNames(String startsWith) {
        Query query = entityManager.createQuery("select distinct p.firstName from Person p where lower(p.firstName) like :startsWith order by p.firstName");
        query.setParameter("startsWith", startsWith + "%").setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }

}
