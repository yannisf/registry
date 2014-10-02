package fraglab.school.guardian;

import fraglab.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GuardianDaoImpl extends GenericDaoImpl<Guardian, Long> implements GuardianDao {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Guardian> fetchAll() {
        LOG.debug("Fetching all Guardian(s)");
        Query query = entityManager.createQuery("select g from Guardian g");
        return query.getResultList();
    }

}
