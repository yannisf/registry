package fraglab.registry.child;

import fraglab.data.GenericDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl extends GenericDaoImpl<Child, String> implements ChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChildDaoImpl.class);

    @Override
    public List<Child> fetchClassroom(String id) {
        LOG.debug("Fetching Children for Classroom [{}]", id);
        String childQuery = "select c from Child c where c.childGroup.id = :childGroupId order by c.level desc, c.genre desc, c.lastName";
        TypedQuery<Child> query = entityManager.createQuery(childQuery, Child.class)
                .setParameter("childGroupId", id);
        return query.getResultList();
    }

    @Override
    public void delete(Child child) {
        LOG.debug("Deleting Child [{}]", child.getId());
        Query deleteChildRelationships = entityManager.createQuery("delete from Relationship r where r.childId=:childId");
        deleteChildRelationships.setParameter("childId", child.getId());
        int relationshipsToDelete = deleteChildRelationships.executeUpdate();
        LOG.debug("While deleting Child[{}], [{}] relationships with Guardians will be deleted as well. ",
                child.getId(), relationshipsToDelete);
        super.delete(child);
    }
}
