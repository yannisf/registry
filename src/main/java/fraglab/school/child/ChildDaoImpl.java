package fraglab.school.child;

import fraglab.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl extends GenericDaoImpl<Child, Long> implements ChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChildDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Child> fetchAll() {
        LOG.debug("Fetching all Child(ren)");
        Query query = entityManager.createQuery("select c from Child c");
        return query.getResultList();
    }

}
