package fraglab.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Repository
@Transactional
public class GenericDaoImpl<T extends Serializable, PK extends Serializable> implements GenericDao<T, PK> {

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
    public T create(T t) {
        LOG.debug("Creating entity [{}]", t);
        entityManager.persist(t);
        return t;
    }

    @Override
    public T fetch(PK id) {
        LOG.debug("Fetching entity with id [{}]", id);
        return entityManager.find(entityClass, id);

    }

    @Override
    public T update(T t) {
        LOG.debug("Updating entity [{}]", t);
        return entityManager.merge(t);
    }

    @Override
    public void delete(T t) {
        LOG.debug("Removing entity [{}]", t);
        t = entityManager.merge(t);
        entityManager.remove(t);
    }

}
