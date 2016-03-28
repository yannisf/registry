package fraglab.registry.typeahead;

import java.util.List;

public interface TypeAheadService {

    Integer MAX_RESULTS = 5;

    String QUERY_WILDCARD = "%";

    List<String> findMatchingFirstNames(String startsWith);

    List<String> findMatchingLastNames(String startsWith);

    List<String> findMatchingProfessions(String startsWith);

    List<String> findMatchingNationalities(String startsWith);

    List<String> findMatchingStreetNames(String startsWith);

    List<String> findMatchingNeighbourhoods(String startsWith);

    List<String> findMatchingCities(String startsWith);
}
