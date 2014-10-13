package fraglab.school.typeahead;

import java.util.List;

public interface TypeAheadService {

    List<String> findMatchingFirstNames(String startsWith);

}
