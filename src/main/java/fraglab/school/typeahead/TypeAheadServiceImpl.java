package fraglab.school.typeahead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAheadServiceImpl implements TypeAheadService {

    @Autowired
    TypeAheadDao typeAheadDao;

    @Override
    public List<String> findMatchingFirstNames(String startsWith) {
        return typeAheadDao.findMatchingFirstNames(startsWith.toLowerCase());
    }

}
