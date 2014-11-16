package fraglab.registry.school;

import java.util.List;

public interface SchoolDao {

    void execute();

    List<SchoolTreeElement> select();

}
