package fraglab.data;

import java.util.List;
import java.util.Map;

public interface GenericDao {

    <T> void createOrUpdate(T t);

    <T> T fetch(Class<T> clazz, String id);

    <T> void delete(T t);

    Long countByQuery(String query, Map<String, Object> params);

    <T> List<T> findByQuery(String query);

    <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params);

    <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, Object> params, Integer maxResults);

    Object[] findSingleByNativeQuery(String query, Map<String, Object> params);

    void executeUpdate(String query, Map<String, Object> params);

}
