package fraglab.school.typeahead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/typeahead")
public class TypeAheadController {

    @Autowired
    TypeAheadService typeAheadService;

    @RequestMapping(value = "/firstnames", method = RequestMethod.GET)
    public List<String> getMatchingFirstNames(@RequestParam(value = "search", required = true) String startsWith) {
        return typeAheadService.findMatchingFirstNames(startsWith);
    }

}
