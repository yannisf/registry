package fraglab.registry.typeahead;

import fraglab.registry.common.BaseEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAheadServiceImpl implements TypeAheadService {

    @Autowired
    private BaseEntityJpaRepository baseEntityJpaRepository;

    @Override
    public List<String> findMatchingFirstNames(String startsWith) {
        return baseEntityJpaRepository.queryByFirstName(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingLastNames(String startsWith) {
        return baseEntityJpaRepository.queryByLastName(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingProfessions(String startsWith) {
        return baseEntityJpaRepository.queryByProfession(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingNationalities(String startsWith) {
        return baseEntityJpaRepository.queryByNationality(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingStreetNames(String startsWith) {
        return baseEntityJpaRepository.queryByStreetName(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingNeighbourhoods(String startsWith) {
        return baseEntityJpaRepository.queryByNeighbourhood(prepareParam(startsWith), preparePageable());
    }

    @Override
    public List<String> findMatchingCities(String startsWith) {
        return baseEntityJpaRepository.queryByCity(prepareParam(startsWith), preparePageable());
    }

    private String prepareParam(String startsWith) {
        return startsWith.toLowerCase() + QUERY_WILDCARD;
    }

    private Pageable preparePageable() {
        return new PageRequest(0, MAX_RESULTS);
    }

}
