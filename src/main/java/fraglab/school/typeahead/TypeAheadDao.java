package fraglab.school.typeahead;

import java.util.List;

public interface TypeAheadDao {

    List<String> findMatchingFirstNames(String startsWith);

}
