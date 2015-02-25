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
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GenericDaoImpl<T extends BaseEntity> implements GenericDao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings(value = "unchecked")
    public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void createOrUpdate(T entity) {
        LOG.debug("Update or create [{}] with id [{}]", entityClass.getName(), entity.getId());
        entityManager.merge(entity);
    }

    @Override
    public T fetch(String id) {
        LOG.debug("Fetch [{}] with id [{}]", entityClass.getName(), id);
        return entityManager.find(entityClass, id);
    }

    @Override
    public void delete(T entity) {
        LOG.debug("Remove [{}] with id [{}]", entityClass.getName(), entity.getId());
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
    public List<T> findByQuery(String query) {
        return null;
    }

    @Override
    public List<T> findByQuery(String query, Map<String, Object> params) {
        TypedQuery<T> typedQuery = getTypedQuery(query, params);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> findByQuery(String query, Map<String, Object> params, Integer maxResults) {
        TypedQuery<T> typedQuery = getTypedQuery(query, params);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    @Override
    public Object[] findSingleByNativeQuery(String query, Map<String, Object> params) {
        Query nativeQuery = entityManager.createNativeQuery(query);
        params.entrySet().stream().forEach(e -> nativeQuery.setParameter(e.getKey(), e.getValue()));
        return (Object[]) nativeQuery.getSingleResult();
    }

    private TypedQuery<T> getTypedQuery(String query, Map<String, Object> params) {
        LOG.debug("Query [{}] with parameter map [{}]", query, params.toString());
        TypedQuery<T> typedQuery = entityManager.createQuery(query, entityClass);
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
