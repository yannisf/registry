package fraglab.registry.child;

import fraglab.data.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl extends GenericDaoImpl<Child, String> implements ChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChildDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Child> fetchAll() {
        LOG.debug("Fetching all Child(ren)");
        Query query = entityManager.createQuery("select c from Child c order by c.level desc, c.genre desc, c.lastName");
        return query.getResultList();
    }

    @Override
    public List<Child> fetchClassroom(String id) {
        LOG.debug("Fetching Children for class {}", id);
        Query query = entityManager.createQuery("select c from Child c where c.childGroup.id = :childGroupId " +
                "order by c.level desc, c.genre desc, c.lastName")
                .setParameter("childGroupId", id);
        return query.getResultList();
    }

    @Override
    public void delete(Child child) {
        Query deleteChildRelationships = entityManager.createQuery("delete from Relationship r where r.childId=:childId");
        deleteChildRelationships.setParameter("childId", child.getId());
        int relationshipsToDelete = deleteChildRelationships.executeUpdate();
        LOG.debug("While deleting Child[{}], [{}] relationships with Guardians will be deleted as well. ",
                child.getId(), relationshipsToDelete);
        super.delete(child);
    }
}
