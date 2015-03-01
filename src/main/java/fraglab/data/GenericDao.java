package fraglab.data;

import fraglab.registry.common.BaseEntity;

import java.util.List;
import java.util.Map;

public interface GenericDao {

    <T extends BaseEntity> void createOrUpdate(T t);

    <T extends BaseEntity> T fetch(Class<T> clazz, String id);

    <T extends BaseEntity> void delete(T t);

    Long countByQuery(String query, Map<String, Object> params);

    <T> List<T> findByQuery(Class<T> clazz, String query);

    <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params);

    <T> T findSingleByQuery(Class<T> clazz, String query, Map<String, Object> params);

    <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params, Integer maxResults);

    Object[] findSingleByNativeQuery(String query, Map<String, Object> params);

    void executeUpdate(String query, Map<String, Object> params);

}
