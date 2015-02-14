package fraglab.data;

import java.io.Serializable;

public interface GenericDao<T extends Serializable, PK extends Serializable> {

    T create(T t);

    T fetch(PK id);

    T update(T t);

    void delete(T t);

}
