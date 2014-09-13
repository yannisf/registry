package fraglab.school.child;

import fraglab.school.model.Child;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl implements ChildDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Child fetch(Long id) {
        System.out.println("Child fetched: " + id);
        Child child = Child.defaultChildFactory();
        child.setId(id);

        return child;
    }

    @Override
    public List<Child> fetchAll() {
        Query query = em.createQuery("select c from Child c");
        return query.getResultList();
    }

    @Override
    public void create(Child child) {
        System.out.println("Child created: " + child);
        em.persist(child);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Child deleted: " + id);
        Child child = em.getReference(Child.class, id);
        em.remove(child);

    }

    @Override
    public void update(Child child) {
        System.out.println("Child updated: " + child);
        em.merge(child);
    }

}
