package fraglab.school.typeahead;

import java.util.List;

public interface TypeAheadDao {

    List<String> findMatchingFirstNames(String startsWith);

    List<String> findMatchingLastNames(String startsWith);

    List<String> findMatchingProfessions(String startsWith);

    List<String> findMatchingNationalities(String startsWith);
}
