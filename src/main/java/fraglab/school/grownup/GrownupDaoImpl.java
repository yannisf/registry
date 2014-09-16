package fraglab.school.grownup;

import fraglab.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GrownupDaoImpl extends GenericDaoImpl<Grownup, Long> implements GrownupDao {

    private static final Logger LOG = LoggerFactory.getLogger(GrownupDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Grownup> fetchAll() {
        LOG.debug("Fetching all GrownUp(s)");
        Query query = entityManager.createQuery("select g from GrownUp g");
        return query.getResultList();
    }

}
