package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolDao schoolDao;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void post() {
        schoolDao.execute();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SchoolTreeElement> fetchSchoolTreeElements() {
        return schoolService.fetchSchoolTreeElements();
    }

    @RequestMapping(value = "/info/{yearClassId}", method = RequestMethod.GET)
    public SchoolData yearClassInformation(@PathVariable String yearClassId) {
        return schoolService.fetchSchoolData(yearClassId);
    }

}
