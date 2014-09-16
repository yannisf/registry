package fraglab.school.child;

import fraglab.GenericDaoImpl;
import fraglab.school.affinity.AffinityDto;
import fraglab.school.affinity.ChildGrownUpAffinity;
import fraglab.school.grownup.Grownup;
import fraglab.school.grownup.GrownupDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ChildDaoImpl extends GenericDaoImpl<Child, Long> implements ChildDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChildDaoImpl.class);

    @Autowired
    private GrownupDao grownupDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Child> fetchAll() {
        LOG.debug("Fetching all Child(ren)");
        Query query = entityManager.createQuery("select c from Child c");
        return query.getResultList();
    }

    @Override
    public ChildGrownUpAffinity fetch(Long childId, Long grownupId) {
        LOG.debug("Fetching affinity for Child[{}]:Grownup[{}]", childId, grownupId);
        Query query = entityManager.createQuery("select a from ChildGrownUpAffinity a where a.childId=:childId and a.grownupId=:grownupId");
        query.setParameter("childId", childId);
        query.setParameter("grownupId", grownupId);
        return (ChildGrownUpAffinity) query.getSingleResult();
    }

    @Override
    public void delete(Long childId, Long grownupId) {
        ChildGrownUpAffinity affinity = fetch(childId, grownupId);
        entityManager.remove(affinity);
    }

    @Override
    public void create(ChildGrownUpAffinity childGrownUpAffinity) {
        entityManager.persist(childGrownUpAffinity);
    }

    @Override
    public List<AffinityDto> fetchAffinities(Long childId) {
        Query query = entityManager.createQuery("select a from ChildGrownUpAffinity a where a.childId=:childId");
        query.setParameter("childId", childId);
        List<ChildGrownUpAffinity> affinities = query.getResultList();
        List<AffinityDto> affinityDtos = getAffinityDtos(affinities);
        return affinityDtos;
    }

    private List<AffinityDto> getAffinityDtos(List<ChildGrownUpAffinity> affinities) {
        List<AffinityDto> affinityDtos = new ArrayList<>();
        for (ChildGrownUpAffinity childGrownUpAffinity : affinities) {
            Grownup grownup = grownupDao.fetch(childGrownUpAffinity.getGrownupId());
            AffinityDto affinityDto = new AffinityDto(grownup.getId(), grownup.getFirstName(), grownup.getLastName(),
                    childGrownUpAffinity.getAffinityMetadata().getType());
            affinityDtos.add(affinityDto);
        }
        return affinityDtos;
    }
}
