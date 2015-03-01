package fraglab.data;

import fraglab.registry.common.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GenericDaoImpl implements GenericDao {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public <T extends BaseEntity> void createOrUpdate(T entity) {
        LOG.debug("Update or create [{}]: [{}]", entity.getClass().getName(), entity.toString());
        entityManager.merge(entity);
    }

    @Override
    public <T extends BaseEntity> T fetch(Class<T> clazz, String id) {
        LOG.debug("Fetch [{}] with id [{}]", clazz.getName(), id);
        return entityManager.find(clazz, id);
    }

    @Override
    public <T extends BaseEntity> void delete(T entity) {
        LOG.debug("Remove [{}]: [{}]", entity.getClass().getName(), entity.toString());
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
    }

    @Override
    public Long countByQuery(String query, Map<String, Object> params) {
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        params.entrySet().stream().forEach(e -> typedQuery.setParameter(e.getKey(), e.getValue()));
        return typedQuery.getSingleResult();
    }

    @Override
    public <T> List<T> findByQuery(Class<T> clazz, String query) {
        TypedQuery<T> typedQuery = getTypedQuery(clazz, query, new HashMap<>());
        return typedQuery.getResultList();
    }

    @Override
    public <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params) {
        TypedQuery<T> typedQuery = getTypedQuery(clazz, query, params);
        return typedQuery.getResultList();
    }

    @Override
    public <T> T findSingleByQuery(Class<T> clazz, String query, Map<String, Object> params) {
        TypedQuery<T> typedQuery = getTypedQuery(clazz, query, params);
        return typedQuery.getSingleResult();
    }

    @Override
    public <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params, Integer maxResults) {
        TypedQuery<T> typedQuery = getTypedQuery(clazz, query, params);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    @Override
    public Object[] findSingleByNativeQuery(String query, Map<String, Object> params) {
        Query nativeQuery = entityManager.createNativeQuery(query);
        params.entrySet().stream().forEach(e -> nativeQuery.setParameter(e.getKey(), e.getValue()));
        return (Object[]) nativeQuery.getSingleResult();
    }

    private <T> TypedQuery<T> getTypedQuery(Class<T> clazz, String query, Map<String, Object> params) {
        LOG.debug("Query [{}] with parameter map [{}]", query, params.toString());
        TypedQuery<T> typedQuery = entityManager.createQuery(query, clazz);
        params.entrySet().stream().forEach(e -> typedQuery.setParameter(e.getKey(), e.getValue()));
        return typedQuery;
    }

    @Override
    public void executeUpdate(String query, Map<String, Object> params) {
        LOG.debug("Executing [{}] with parameter map [{}]", query, params.toString());
        Query emQuery = entityManager.createQuery(query);
        params.entrySet().stream().forEach(e -> emQuery.setParameter(e.getKey(), e.getValue()));
        emQuery.executeUpdate();
    }

}
