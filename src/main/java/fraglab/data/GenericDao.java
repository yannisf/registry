package fraglab.data;

import fraglab.registry.common.BaseEntity;

import java.util.List;
import java.util.Map;

public interface GenericDao<T extends BaseEntity> {

    void createOrUpdate(T t);

    T fetch(String id);

    void delete(T t);

    Long countByQuery(String query, Map<String, Object> params);

    List<T> findByQuery(String query);

    List<T> findByQuery(String query, Map<String, Object> params);

    List<T> findByQuery(String query, Map<String, Object> params, Integer maxResults);

    Object[] findSingleByNativeQuery(String query, Map<String, Object> params);

    void executeUpdate(String query, Map<String, Object> params);

}
